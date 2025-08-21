package org.yojung.diary.aimode.service.impl

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.yojung.diary.aimode.domain.AiMode
import org.yojung.diary.aimode.dto.AiModeRegisterRequest
import org.yojung.diary.aimode.dto.AiModeResponse
import org.yojung.diary.aimode.dto.AiModeUpdateRequest
import org.yojung.diary.aimode.exception.AiModeNotFoundException
import org.yojung.diary.aimode.mapper.AiModeMapper
import org.yojung.diary.aimode.repository.AiModeRepository
import org.yojung.diary.aimode.service.AiModeService
import org.yojung.diary.storage.ObjectStorage

@Service
class AiModeServiceImpl(
    private val aiModeRepository: AiModeRepository,
    private val aiModeMapper: AiModeMapper,
    private val objectStorage: ObjectStorage
) : AiModeService {

    @Transactional
    override fun createAiMode(request: AiModeRegisterRequest): AiModeResponse {
        var imageUrl: String? = null
        val aiMode = aiModeMapper.toEntity(request)
        imageUrl = objectStorage.uploadFile(request.image)
        aiMode.update(
            mode = request.mode,
            label = request.label,
            description = request.description,
            imageUrl = imageUrl,
            prompt = request.prompt,
            colorLabel = request.colorLabel,
            colorBg = request.colorBg,
            creditAmount = request.creditAmount
        )
        val saved = aiModeRepository.save(aiMode)
        return aiModeMapper.toResponse(saved)
    }

    @Transactional
    override fun updateAiMode(aiMode: String, request: AiModeUpdateRequest): AiModeResponse {
        val aiMode = aiModeRepository.findByMode(request.mode).orElseThrow({AiModeNotFoundException(request.mode)}) as AiMode
        var imageUrl: String = aiMode.getImageUrl()
        if (!request.image.isEmpty) {
            objectStorage.deleteFile(aiMode.getImageUrl())
            imageUrl = objectStorage.uploadFile(request.image)
        }
        aiMode.update(
            mode = request.mode,
            label = request.label,
            description = request.description,
            imageUrl = imageUrl,
            prompt = request.prompt,
            colorLabel = request.colorLabel,
            colorBg = request.colorBg,
            creditAmount = request.creditAmount
        )
        return aiModeMapper.toResponse(aiMode)
    }

    override fun getAiMode(aiModeId: Int): AiModeResponse {
        val aiMode = findAiModeById(aiModeId)
        return aiModeMapper.toResponse(aiMode)
    }

    override fun getAiModes(pageable: Pageable): Page<AiModeResponse> {
        return aiModeRepository.findAll(pageable).map(aiModeMapper::toResponse)
    }

    private fun findAiModeById(aiModeId: Int): AiMode {
        return aiModeRepository.findById(aiModeId).orElseThrow { AiModeNotFoundException(aiModeId) }
    }
}

