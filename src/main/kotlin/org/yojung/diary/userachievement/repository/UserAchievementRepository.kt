package org.yojung.diary.userachievement.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.yojung.diary.userachievement.domain.UserAchievement

interface UserAchievementRepository : JpaRepository<UserAchievement, Long>

