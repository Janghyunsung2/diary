package org.yojung.diary.storage

import com.daily.storeage.util.ImageConverter
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import org.yojung.diary.storage.dto.ImageConvertResult
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import java.net.URI
import java.util.UUID

@Profile("blue", "green")
@Service
class AwsStorage(
    private val s3Client: S3Client,
    @Value("\${aws.bucket}") private val bucketName: String

) : ObjectStorage {

    override fun uploadFile(multipartFile: MultipartFile?): String? {
        val imageResult = ImageConverter.convertImageToWebpBytes(multipartFile)

        // 변환 성공 여부에 따라 확장자와 컨텐츠 타입 결정
        val extension = imageResult?.let { if (it.converted) "webp" else imageResult.extension }
        val contentType = imageResult?.let { if (it.converted) "image/webp" else getContentType(imageResult.extension) }

        val key = "uploads/${UUID.randomUUID()}.$extension"

        val putReq = PutObjectRequest.builder()
            .bucket(bucketName)
            .key(key)
            .contentType(contentType)
            .build()

        imageResult?.let {
            s3Client.putObject(
                putReq,
                RequestBody.fromBytes(it.bytes)
            )
        }

        val url = s3Client.utilities().getUrl { it.bucket(bucketName).key(key) }
        return url.toExternalForm()
    }

    private fun getContentType(extension: String): String {
        return when (extension.lowercase()) {
            "jpg", "jpeg" -> "image/jpeg"
            "png" -> "image/png"
            "gif" -> "image/gif"
            "bmp" -> "image/bmp"
            "webp" -> "image/webp"
            else -> "application/octet-stream"
        }
    }

    override fun deleteFile(fileUrl: String?): Boolean {
        return try {
            val key = URI(fileUrl).path.removePrefix("/")
            val delReq = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build()
            s3Client.deleteObject(delReq)
            true
        } catch (_: Exception) {
            false
        }
    }
}