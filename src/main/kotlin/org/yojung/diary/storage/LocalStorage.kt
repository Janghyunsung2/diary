package org.yojung.diary.storage

import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service

@Profile("local")
@Service
class LocalStorage : ObjectStorage {
    override fun uploadFile(multipartFile: org.springframework.web.multipart.MultipartFile): String? {
        // 로컬 스토리지에 파일 저장 로직 구현 (예: 파일 시스템에 저장)
        // 예시로 파일 이름을 반환
        return multipartFile.originalFilename
    }

    override fun deleteFile(fileUrl: String): Boolean {
        // 로컬 스토리지에서 파일 삭제 로직 구현 (예: 파일 시스템에서 삭제)
        // 예시로 항상 true 반환
        return true
    }
}