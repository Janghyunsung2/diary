package org.yojung.diary.achievement.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.yojung.diary.achievement.domain.Achievement

interface AchievementRepository : JpaRepository<Achievement, String> {


    @Modifying
    fun deleteAchievementByCode(code : String)
}