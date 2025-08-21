package org.yojung.diary.credittransaction.dto

import java.math.BigDecimal

// 응답 DTO
data class CreditTransactionResponse(
    val id: Long?,
    val userId: Long,
    val amount: Int,
    val reason: String,
    val achievementCode: String?,
    val paymentCode: String?,
    val createdAt: String
)

