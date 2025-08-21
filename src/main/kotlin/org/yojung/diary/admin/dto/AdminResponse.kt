package org.yojung.diary.admin.dto

import java.time.ZonedDateTime

data class AdminResponse(
    val id: Int?,
    val name: String,
    val email: String,
    val createdAt: ZonedDateTime?,
    val updatedAt: ZonedDateTime?
)

