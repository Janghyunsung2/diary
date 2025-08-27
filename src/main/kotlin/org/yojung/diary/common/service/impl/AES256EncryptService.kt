package org.yojung.diary.common.service.impl

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.yojung.diary.common.service.EncryptService
import java.util.Base64
import javax.crypto.Cipher

@Service
class AES256EncryptService : EncryptService {

    @Value("\${crypto.aes.key}")
    private val key: String? = null

    @Value("\${crypto.aes.iv}")
    private val iv: String? = null

    private val algorithm = "AES/CBC/PKCS5Padding"

    override fun encrypt(value: String): String {

        try {
            val cipher = Cipher.getInstance(algorithm)
            val secretKeySpec = key?.let { javax.crypto.spec.SecretKeySpec(it.toByteArray(), "AES") }
            val ivParameterSpec = iv?.let { javax.crypto.spec.IvParameterSpec(it.toByteArray()) }
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec)
            val encrypted = cipher.doFinal(value.toByteArray())
            return Base64.getEncoder().encodeToString(encrypted);
        }catch (e:Exception){
            throw RuntimeException("Encryption failed", e)
        }
    }
    override fun decrypt(value: String): String {
        try {
            val cipher = Cipher.getInstance(algorithm)
            val secretKeySpec = key?.let { javax.crypto.spec.SecretKeySpec(it.toByteArray(), "AES") }
            val ivParameterSpec = iv?.let { javax.crypto.spec.IvParameterSpec(it.toByteArray()) }
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec)
            val decodedValue = Base64.getDecoder().decode(value)
            val decrypted = cipher.doFinal(decodedValue)
            return String(decrypted)
        } catch (e: Exception) {
            throw RuntimeException("Decryption failed", e)
        }
    }
}