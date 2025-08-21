package org.yojung.diary.admin.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.yojung.diary.admin.domain.Admin
import java.util.Optional

interface AdminRepository : JpaRepository<Admin, Int> {
    fun findByEmail(email: String): Optional<Admin>
}

