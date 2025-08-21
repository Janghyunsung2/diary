package org.yojung.diary.common.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

data class CustomUserDetails(
    private val id: Long,
    private val email: String,
    private val password: String,
    private val role: String,
    private val isAdmin: Boolean = false
) : UserDetails {

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return if (isAdmin) {
            listOf(SimpleGrantedAuthority("ROLE_ADMIN"))
        } else {
            listOf(SimpleGrantedAuthority("ROLE_USER"))
        }
    }

    override fun getPassword(): String = password
    override fun getUsername(): String = email
    override fun isAccountNonExpired(): Boolean = true
    override fun isAccountNonLocked(): Boolean = true
    override fun isCredentialsNonExpired(): Boolean = true
    override fun isEnabled(): Boolean = true

    fun getId(): Long = id
    fun getRole(): String = role
    fun isAdmin(): Boolean = isAdmin
}
