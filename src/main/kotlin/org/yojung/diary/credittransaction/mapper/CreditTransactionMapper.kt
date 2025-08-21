package org.yojung.diary.credittransaction.mapper

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings
import org.yojung.diary.credittransaction.domain.CreditTransaction
import org.yojung.diary.credittransaction.dto.CreditTransactionRegisterRequest
import org.yojung.diary.credittransaction.dto.CreditTransactionResponse

@Mapper(componentModel = "spring")
interface CreditTransactionMapper {

    @Mappings
    fun toEntity(request: CreditTransactionRegisterRequest): CreditTransaction

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "achievementCode", source = "achievement.code")
    fun toResponse(entity: CreditTransaction): CreditTransactionResponse
}

