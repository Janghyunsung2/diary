package org.yojung.diary.credittransaction.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.yojung.diary.credittransaction.dto.CreditTransactionRegisterRequest
import org.yojung.diary.credittransaction.dto.CreditTransactionResponse

interface CreditTransactionService {
    fun registerTransaction(request: CreditTransactionRegisterRequest): CreditTransactionResponse
    fun getTransaction(id: Long): CreditTransactionResponse
    fun getTransactions(pageable: Pageable): Page<CreditTransactionResponse>
    fun getCreditTransactionByMe(userId: Long): Int
}

