package org.yojung.diary.credittransaction.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.yojung.diary.credittransaction.domain.CreditTransaction

interface CreditTransactionRepository : JpaRepository<CreditTransaction, Long>{
    fun findByUserId(userId: Long): List<CreditTransaction>
}

