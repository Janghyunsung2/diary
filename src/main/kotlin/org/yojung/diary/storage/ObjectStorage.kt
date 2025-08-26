package org.yojung.diary.storage

import org.springframework.web.multipart.MultipartFile

interface ObjectStorage {


    fun uploadFile(multipartFile: MultipartFile): String?

    fun deleteFile(fileUrl: String): Boolean
}