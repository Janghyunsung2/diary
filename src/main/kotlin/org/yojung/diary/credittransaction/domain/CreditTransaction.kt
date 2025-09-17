package org.yojung.diary.credittransaction.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.hibernate.annotations.CreationTimestamp
import org.yojung.diary.achievement.domain.Achievement
import org.yojung.diary.user.domain.User
import java.time.ZonedDateTime

@Entity
@Table(name = "credit_transactions")
class CreditTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "credit_transaction_id")
    private var id : Long? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private var user: User? = null;

    @Column(name = "amount", nullable = false)
    private var amount: Int? = 0

    @Column(name = "reason", nullable = false)
    private var reason: String? = ""

    @Column(name = "payment_code", nullable = true)
    private var paymentCode: String? = ""

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "achievement_code", nullable = true)
    private var achievement: Achievement? = null

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private var createdAt: java.time.ZonedDateTime? = null;

    constructor(
        user: User,
        amount: Int?,
        reason: String,
        paymentCode: String? = null,
        achievement: Achievement? = null
    ) {
        this.user = user
        this.amount = amount
        this.reason = reason
        this.paymentCode = paymentCode
        this.achievement = achievement
    }

    constructor() {
        // Default constructor for JPA
    }
    fun getId(): Long? {
        return id
    }
    fun getUser(): User? {
        return user
    }
    fun getReason(): String? {
        return reason
    }
    fun getPaymentCode(): String? {
        return paymentCode
    }
    fun getAchievement(): Achievement? {
        return achievement
    }
    fun getCreatedAt(): ZonedDateTime? {
        return createdAt
    }


    fun getAmount(): Int? {
        return amount
    }

    fun setUser(user: User) {
        this.user = user
    }

}