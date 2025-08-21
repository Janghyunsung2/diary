package org.yojung.diary.achievement.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.CreationTimestamp
import java.time.ZonedDateTime

@Entity
@Table(name = "achievements")
class Achievement {

    @Id
    @Column(name = "code", nullable = false)
    var code: String = ""

    @Column(name = "nextCode", nullable = true)
    var nextCode: String? = null

    @Column(name = "name", nullable = false)
    var name: String = ""

    @Column(name = "description", nullable = false)
    var description: String = ""

    @Column(name = "credit_reward", nullable = false)
    var creditReward: Int = 0

    @Column(name = "iconUrl", nullable = true)
    var iconUrl: String? = null

    @Column(name = "progress_value", nullable = false)
    var progressValue: Int = 0

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    var createdAt: ZonedDateTime? = null

    constructor(
        code: String,
        nextCode: String?,
        name: String,
        description: String,
        creditReward: Int,
        iconUrl: String?,
        progressValue: Int
    ) {
        this.code = code
        this.nextCode = nextCode
        this.name = name
        this.description = description
        this.creditReward = creditReward
        this.iconUrl = iconUrl
        this.progressValue = progressValue
    }

    constructor() {
        // Default constructor for JPA
    }

    fun update(
        nextCode: String?,
        name: String,
        description: String,
        creditReward: Int,
        iconUrl: String?,
        progressValue: Int
    ) {
        this.nextCode = nextCode
        this.name = name
        this.description = description
        this.creditReward = creditReward
        this.iconUrl = iconUrl
        this.progressValue = progressValue
    }

    fun addIconUrl(iconUrl: String) {
        this.iconUrl = iconUrl
    }
}