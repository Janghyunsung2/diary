package org.yojung.diary.admin.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.yojung.diary.admin.dto.AdminResponse

interface AdminService {
    fun getAdmins(pageable: Pageable): Page<AdminResponse>
    fun authenticateAdmin(email: String, password: String): Boolean
}

