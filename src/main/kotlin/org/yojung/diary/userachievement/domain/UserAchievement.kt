package org.yojung.diary.userachievement.domain

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
import java.time.ZonedDateTime

@Entity
@Table(name = "user_achievements")
class UserAchievement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_achievement_id")
    var id : Long? = null

    @Column(name = "user_id", nullable = false)
    var userId: Long? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "achievement_code", nullable = false)
    var achievement: Achievement? = null

    @Column(name = "current_value", nullable = false)
    var currentValue: Int = 0

    @Column(name = "goal_value", nullable = false)
    var goalValue: Int = 0

    @Column(name = "completed", nullable = false)
    var completed: Boolean = false

    @Column(name = "reward_granted", nullable = true)
    var rewardGranted: Boolean = false

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    var createdAt: ZonedDateTime? = null

    constructor(
        userId: Long,
        achievement: Achievement,
        currentValue: Int,
        goalValue: Int,
        completed: Boolean = false,
        rewardGranted: Boolean = false
    ) {
        this.userId = userId
        this.achievement = achievement
        this.currentValue = currentValue
        this.goalValue = goalValue
        this.completed = completed
        this.rewardGranted = rewardGranted
    }

    constructor() {
        // Default constructor for JPA
    }

    fun increaseProgress(){
        this.currentValue++;
        if(this.currentValue >= this.goalValue) {
            this.completed = true;
        }
    }

    fun grantReward() {
        if (this.completed && !this.rewardGranted) {
            this.rewardGranted = true;
        }
    }



}