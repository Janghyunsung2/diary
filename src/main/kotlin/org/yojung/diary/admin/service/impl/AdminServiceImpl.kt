package org.yojung.diary.admin.service.impl

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.yojung.diary.admin.dto.AdminResponse
import org.yojung.diary.admin.exception.AdminNotFoundException
import org.yojung.diary.admin.repository.AdminRepository
import org.yojung.diary.admin.service.AdminService

@Service
class AdminServiceImpl(
    private val adminRepository: AdminRepository,
    private val passwordEncoder: PasswordEncoder
) : AdminService {
    override fun getAdmins(pageable: Pageable): Page<AdminResponse> {
        return adminRepository.findAll(pageable).map { a ->
            AdminResponse(
                id = a.id,
                name = a.name,
                email = a.email,
                createdAt = a.createdAt,
                updatedAt = a.updatedAt
            )
        }
    }

    override fun authenticateAdmin(email: String, password: String): Boolean {
        val admin = adminRepository.findByEmail(email).orElseThrow { AdminNotFoundException(email) }
        return passwordEncoder.matches(password, admin.password)
    }
}

