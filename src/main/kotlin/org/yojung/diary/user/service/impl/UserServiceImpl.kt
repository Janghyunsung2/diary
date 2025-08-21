package org.yojung.diary.user.service.impl

import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.yojung.diary.storage.ObjectStorage
import org.yojung.diary.user.domain.Gender
import org.yojung.diary.user.domain.User
import org.yojung.diary.user.dto.UserRegisterRequest
import org.yojung.diary.user.dto.UserResponse
import org.yojung.diary.user.dto.UserUpdateRequest
import org.yojung.diary.user.exception.UserBadRequestException
import org.yojung.diary.user.exception.UserNotFoundException
import org.yojung.diary.user.mapper.UserMapper
import org.yojung.diary.user.repository.UserRepository
import org.yojung.diary.user.service.UserService

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val objectStorage: ObjectStorage,
    private val userMapper: UserMapper
) : UserService {

    @Transactional
    override fun registerUser(request: UserRegisterRequest): UserResponse {
        val user = User(
            oauthId = request.oauthId,
            provider = request.provider,
            nickname = request.nickname,
            profileImage = request.profileImage,
            gender = request.gender
        )
        val saved = userRepository.save(user)
        return UserResponse(
            id = saved.getId(),
            oauthId = saved.getOauthId(),
            provider = saved.getProvider(),
            nickname = saved.getNickname(),
            profileImage = saved.getProfileImage(),
            gender = saved.getGender()
        )
    }

    @Transactional(readOnly = true)
    override fun getUser(id: Long): UserResponse {
        val user = userRepository.findById(id).orElseThrow { UserNotFoundException(id) }
        return UserResponse(
            id = user.getId(),
            oauthId = user.getOauthId(),
            provider = user.getProvider(),
            nickname = user.getNickname(),
            profileImage = user.getProfileImage(),
            gender = user.getGender(),
        )
    }

    @Transactional
    override fun updateUser(id: Long, request: UserUpdateRequest): UserResponse {
        val user = userRepository.findById(id).orElseThrow { UserNotFoundException(id) }
        if (request.nickname.isBlank() || request.nickname.length > 20) {
            throw UserBadRequestException("닉네임은 비어있거나 20자 이상일 수 없습니다.")
        }
        user.update(
            nickname = request.nickname,
            gender = request.gender,
            profileImage = request.profileImage
        )
        return UserResponse(
            id = user.getId(),
            oauthId = user.getOauthId(),
            provider = user.getProvider(),
            nickname = user.getNickname(),
            profileImage = user.getProfileImage(),
            gender = user.getGender(),
        )
    }

    override fun existsByNickname(nickname: String): Boolean {
        return userRepository.existsByNickname(nickname)
    }

    override fun getUsers(pageable: Pageable): Page<UserResponse> {
        return userRepository.findAll(pageable).map { userMapper.toResponse(it) }
    }
}

