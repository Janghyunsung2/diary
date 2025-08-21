package org.yojung.diary.user.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.yojung.diary.user.dto.UserRegisterRequest
import org.yojung.diary.user.dto.UserResponse
import org.yojung.diary.user.dto.UserUpdateRequest

interface UserService {
    fun registerUser(request: UserRegisterRequest): UserResponse
    fun getUser(id: Long): UserResponse
    fun updateUser(id: Long, request: UserUpdateRequest): UserResponse
    fun existsByNickname(nickname: String): Boolean

    fun getUsers(pageable: Pageable): Page<UserResponse>
}

