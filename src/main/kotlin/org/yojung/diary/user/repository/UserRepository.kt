package org.yojung.diary.user.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.yojung.diary.user.domain.User
import java.util.Optional

interface UserRepository : JpaRepository<User, Long> {


    fun findFirstByOauthIdAndProviderAndIsActiveTrueOrderByIdDesc(oauthId: String, provider: String): Optional<User>
    fun existsByNickname(nickname: String): Boolean
    fun findByOauthId(oauthId: String): Optional<User>
}
