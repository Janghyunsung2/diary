package org.yojung.diary.common.security

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.yojung.diary.admin.repository.AdminRepository
import org.yojung.diary.user.exception.UserNotFoundException
import org.yojung.diary.user.repository.UserRepository

@Service
class CustomUserDetailsService(
    private val userRepository: UserRepository,
    private val adminRepository: AdminRepository
) : UserDetailsService {


    override fun loadUserByUsername(email: String): UserDetails {
        val admin = adminRepository.findByEmail(email)
            .orElseThrow { UsernameNotFoundException("Admin not found with email: $email") }

        return CustomUserDetails(
            id = admin.id?.toLong() ?: 0L,
            email = admin.email,
            password = admin.password, // 세션 기반 로그인 → password 필요
            role = "ADMIN",
            isAdmin = true
        )
    }


    fun loadByProviderAndProviderId(provider: String, providerId: String): CustomUserDetails {
        val user = userRepository.findFirstByOauthIdAndProviderAndIsActiveTrueOrderByIdDesc(providerId, provider).orElseThrow({ UserNotFoundException("$providerId not found") })

        return CustomUserDetails(
            id = user.getId(),
            email = user.getOauthId(),          // 소셜 로그인 계정의 식별자 (oauthId)
            password = user.getPassword() ?: "", // 비밀번호 없을 수도 있음
            role = "USER",
            isAdmin = false,
            provider = user.getProvider(),
        )
    }
}