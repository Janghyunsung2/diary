package org.yojung.diary.common.converter

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.yojung.diary.common.service.EncryptService

@Component
@Converter(autoApply = false)
class EncryptConverter : AttributeConverter<String, String> {

    private val encryptService: EncryptService

    @Autowired
    constructor(encryptService: EncryptService) {
        this.encryptService = encryptService
    }
    override fun convertToDatabaseColumn(value: String): String {
        return encryptService.encrypt(value);
    }

    override fun convertToEntityAttribute(value: String): String {
        return encryptService.decrypt(value);
    }


}