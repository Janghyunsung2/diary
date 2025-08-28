package com.daily.storeage.util

import com.sksamuel.scrimage.ImmutableImage
import com.sksamuel.scrimage.webp.WebpWriter
import org.springframework.web.multipart.MultipartFile
import org.yojung.diary.storage.dto.ImageConvertResult
import java.io.IOException

object ImageConverter {
    @JvmStatic
    fun convertImageToWebpBytes(file: MultipartFile?): ImageConvertResult? {
        try {
            val originalBytes = file?.bytes
            val image = ImmutableImage.loader().fromBytes(originalBytes)

            val webpWriter = WebpWriter(6, 80, 6, false)

            val bytes = image.bytes(webpWriter)

            return ImageConvertResult(true, bytes, "webp")
        } catch (e: Exception) {
            try {
                val originalBytes = file?.bytes
                val ext = getExtension(file?.originalFilename)
                return originalBytes?.let { ImageConvertResult(false, it, ext) }
            } catch (ex: IOException) {
                throw RuntimeException(ex)
            }
        }
    }

    private fun getExtension(filename: String?): String {
        if (filename == null || !filename.contains(".")) return ""
        return filename.substring(filename.lastIndexOf('.') + 1)
    }
}