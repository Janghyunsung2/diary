package org.yojung.diary.userachievement.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.yojung.diary.userachievement.domain.UserAchievement

interface UserAchievementRepository : JpaRepository<UserAchievement, Long>{

    @Query("SELECT u FROM UserAchievement u WHERE u.userId = :userId")
    fun findByUserId(
        pageable: org.springframework.data.domain.Pageable,
        userId: Long
    ): org.springframework.data.domain.Page<UserAchievement>

    @Query("select distinct u from UserAchievement u where u.achievement.code=:code")
    fun findByCode(code: String): UserAchievement?
}



