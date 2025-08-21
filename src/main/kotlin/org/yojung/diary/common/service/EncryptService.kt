package org.yojung.diary.common.service

interface EncryptService {

    fun encrypt(value: String): String

    fun decrypt(value: String): String

}