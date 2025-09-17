package org.yojung.diary.credittransaction.dto

import jakarta.validation.constraints.NotNull
import java.math.BigDecimal

// 등록 요청 DTO
data class CreditTransactionRegisterRequest(
    @field:NotNull
    val userId: Long,
    @field:NotNull
    val amount: Int?,
    @field:NotNull
    val reason: String,
    val achievementCode: String? = null,
    val paymentCode: String? = null
)

