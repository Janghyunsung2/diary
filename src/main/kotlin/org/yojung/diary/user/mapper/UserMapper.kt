package org.yojung.diary.user.mapper

import org.mapstruct.Mapper
import org.yojung.diary.user.domain.User
import org.yojung.diary.user.dto.UserRegisterRequest
import org.yojung.diary.user.dto.UserResponse

@Mapper(uses = [UserMapper::class])
interface UserMapper {
    fun toEntity(request: UserRegisterRequest): User
    fun toResponse(user: User): UserResponse
}