package org.yojung.diary.diary.mapper

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.yojung.diary.diary.domain.Diary
import org.yojung.diary.diary.dto.DiaryRegisterRequest
import org.yojung.diary.diary.dto.DiaryResponse
import org.yojung.diary.user.repository.UserRepository

@Mapper(componentModel = "spring")
interface DiaryMapper {
    fun toEntity(request: DiaryRegisterRequest): Diary
    @Mapping(target= "userId", source = "user.id")
    fun toResponse(entity: Diary): DiaryResponse
}