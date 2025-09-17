package org.yojung.diary.credittransaction.service.impl

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.yojung.diary.achievement.exception.AchievementNotFoundException
import org.yojung.diary.achievement.repository.AchievementRepository
import org.yojung.diary.common.security.JwtTokenProvider
import org.yojung.diary.credittransaction.domain.CreditTransaction
import org.yojung.diary.credittransaction.dto.CreditTransactionRegisterRequest
import org.yojung.diary.credittransaction.dto.CreditTransactionResponse
import org.yojung.diary.credittransaction.exception.CreditTransactionNotFoundException
import org.yojung.diary.credittransaction.mapper.CreditTransactionMapper
import org.yojung.diary.credittransaction.repository.CreditTransactionRepository
import org.yojung.diary.credittransaction.service.CreditTransactionService
import org.yojung.diary.user.exception.UserNotFoundException
import org.yojung.diary.user.repository.UserRepository

@Service
class CreditTransactionServiceImpl(
    private val creditTransactionRepository: CreditTransactionRepository,
    private val creditTransactionMapper: CreditTransactionMapper,
    private val userRepository: UserRepository,
    private val achievementRepository: AchievementRepository,
) : CreditTransactionService {

    @Transactional
    override fun registerTransaction(request: CreditTransactionRegisterRequest): CreditTransactionResponse {

        val user = userRepository.findById(request.userId).orElseThrow { UserNotFoundException(request.userId) }
        var achievementCode = null
        if(request.achievementCode != null && request.achievementCode.isNotBlank()) {
            achievementCode = request.achievementCode.let { achievementRepository.findById(it) }.orElseThrow( { AchievementNotFoundException(request.achievementCode) }) as Nothing?
        }
        val entity = CreditTransaction(
            user = user,
            amount = request.amount,
            reason = request.reason,
            achievement = achievementCode,
            paymentCode = request.paymentCode
        )
        val saved = creditTransactionRepository.save(entity)
        return creditTransactionMapper.toResponse(saved)
    }

    @Transactional(readOnly = true)
    override fun getTransaction(id: Long): CreditTransactionResponse {
        val entity = creditTransactionRepository.findById(id)
            .orElseThrow { CreditTransactionNotFoundException(id) }
        return creditTransactionMapper.toResponse(entity)
    }

    @Transactional(readOnly = true)
    override fun getTransactions(pageable: Pageable): Page<CreditTransactionResponse> {
        return creditTransactionRepository.findAll(pageable).map(creditTransactionMapper::toResponse)
    }
    @Transactional(readOnly = true)
    override fun getCreditTransactionByMe(userId: Long): Int {
        return creditTransactionRepository.findByUserId(userId).sumOf(
            {i -> i.getAmount() ?: 0 }
        )
    }
}
