package org.yojung.diary.aimode.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.yojung.diary.aimode.domain.AiMode
import java.util.Optional

interface AiModeRepository : JpaRepository<AiMode, Int> {
    fun findByMode(mode: String): Optional<AiMode>
}

