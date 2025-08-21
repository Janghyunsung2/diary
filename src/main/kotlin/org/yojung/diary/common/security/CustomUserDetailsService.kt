package org.yojung.diary.common.security

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.yojung.diary.admin.repository.AdminRepository
import org.yojung.diary.user.repository.UserRepository

@Service
class CustomUserDetailsService(
    private val userRepository: UserRepository,
    private val adminRepository: AdminRepository
) : UserDetailsService {

    override fun loadUserByUsername(email: String): UserDetails {
        // 먼저 관리자 확인
        val admin = adminRepository.findByEmail(email)
        if (admin.isPresent) {
            val adminEntity = admin.get()
            return CustomUserDetails(
                id = adminEntity.id?.toLong() ?: 0L,
                email = adminEntity.email,
                password = adminEntity.password,
                role = "ADMIN",
                isAdmin = true
            )
        }

        // 관리자가 아니면 일반 사용자 확인
        val user = userRepository.findByOauthId(email)
        if (user.isPresent) {
            val userEntity = user.get()
            return CustomUserDetails(
                id = userEntity.getId(),
                email = userEntity.getOauthId(),
                password = userEntity.getPassword() ?: "",
                role = "USER",
                isAdmin = false
            )
        }

        throw UsernameNotFoundException("User not found with email: $email")
    }
}
