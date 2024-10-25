package com.example.demo

import com.example.demo.model.DbChangeEvent
import com.fasterxml.jackson.databind.exc.MismatchedInputException
import kotlinx.coroutines.runBlocking
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.errors.SerializationException
import org.springframework.kafka.annotation.DltHandler
import org.springframework.kafka.annotation.KafkaHandler
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.annotation.RetryableTopic
import org.springframework.kafka.retrytopic.DltStrategy
import org.springframework.kafka.support.Acknowledgment
import org.springframework.kafka.support.serializer.DeserializationException
import org.springframework.retry.annotation.Backoff
import org.springframework.stereotype.Component
import java.net.SocketTimeoutException
import java.time.Duration
import java.time.temporal.ChronoUnit


@Component
@KafkaListener(topics = ["my_dbz_topic.public.student_info"], containerFactory = "kafkaListenerContainerFactory2")
class MySuperConsumer() {

    @RetryableTopic(
        attempts = "5",
        //listenerContainerFactory = "kafkaListenerContainerFactory",
        autoCreateTopics = "false",
        backoff = Backoff(delay = 5000),
        include = [MismatchedInputException::class, DeserializationException::class, SerializationException::class],
        exclude = [NullPointerException::class]
    )
    @KafkaHandler
    fun handle(event: DbChangeEvent) {
        runBlocking {
            try {
                println("StudentDto: $event")
            } catch (e: Exception) {
                println("Error processing message: ${e.message}")
            }
        }
    }

    @DltHandler
    fun deadLetterQueueHandler(message: String?) {
       println(message)
    }
}
