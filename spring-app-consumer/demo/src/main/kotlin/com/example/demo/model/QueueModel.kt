package com.example.demo.model

import org.springframework.retry.annotation.Backoff

@Backoff(delay = 10000, multiplier = 2.0, maxDelay = 60000)
data class DbChangeEvent(
    val before: StudentDto?,
    val after: StudentDto?,
    val source: Source,
    val op: String,
    val ts_ms: Long,
    val transaction: Any?
)

data class StudentDto(
    val id: Int,
    val name: String,
    val subjectArea: String? = null
)

data class Source(
    val version: String?,
    val connector: String?,
    val name: String?,
    val tsMs: Long?,
    val snapshot: String?,
    val db: String,
    val sequence: String? = null,
    val schema: String?,
    val table: String?,
    val txId: Int?,
    val lsn: Long?,
    val xmin: Any?
)
