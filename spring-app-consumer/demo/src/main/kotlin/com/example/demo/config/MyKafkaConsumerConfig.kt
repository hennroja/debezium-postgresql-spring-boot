package com.example.demo.config

import com.example.demo.model.DbChangeEvent
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.ssl.DefaultSslBundleRegistry
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.listener.ContainerProperties
import org.springframework.kafka.listener.DefaultErrorHandler
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer
import org.springframework.kafka.support.serializer.JsonDeserializer
import org.springframework.util.backoff.BackOff
import org.springframework.util.backoff.FixedBackOff
import java.net.SocketTimeoutException


@Configuration
@EnableConfigurationProperties(KafkaProperties::class)
class MyKafkaConsumerConfig(
    private val kafkaProperties: KafkaProperties,
    private val sslBundleRegistry: DefaultSslBundleRegistry
) {
    @Bean
    fun kafkaListenerContainerFactory2(): ConcurrentKafkaListenerContainerFactory<String, DbChangeEvent> {
        val factory: ConcurrentKafkaListenerContainerFactory<String, DbChangeEvent> =
            ConcurrentKafkaListenerContainerFactory()

        val props: MutableMap<String, Any> = kafkaProperties.buildConsumerProperties(sslBundleRegistry)
        val consumerFactory = DefaultKafkaConsumerFactory(
            props, StringDeserializer(), ErrorHandlingDeserializer(
                JsonDeserializer(
                    DbChangeEvent::class.java,
                    false,
                ).apply {
                    addTrustedPackages("*")
                },
            )
        )

        factory.setCommonErrorHandler(DefaultErrorHandler(FixedBackOff(2000L, 3L)))

        factory.containerProperties.ackMode = ContainerProperties.AckMode.MANUAL
        factory.consumerFactory = consumerFactory

        return factory
    }

    @Bean
    fun errorHandler(): DefaultErrorHandler {
        val fixedBackOff: BackOff = FixedBackOff(2000, 3)
        val errorHandler = DefaultErrorHandler(
            { consumerRecord: ConsumerRecord<*, *>?, e: Exception? -> }, fixedBackOff
        )
        errorHandler.addRetryableExceptions(SocketTimeoutException::class.java)
        errorHandler.addNotRetryableExceptions(NullPointerException::class.java)
        return errorHandler
    }
}
