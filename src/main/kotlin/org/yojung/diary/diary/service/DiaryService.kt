package org.yojung.diary.diary.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.yojung.diary.diary.dto.DiaryAndFeedbackResponse
import org.yojung.diary.diary.dto.DiaryRegisterRequest
import org.yojung.diary.diary.dto.DiaryResponse
import org.yojung.diary.diary.dto.DiaryUpdateRequest

interface DiaryService {
    fun createDaily(userId: Long, dailyRegisterRequest: DiaryRegisterRequest): DiaryResponse
    fun updateDaily(dailyId: Long, dailyUpdateRequest: DiaryUpdateRequest): DiaryResponse
    fun getDaily(dailyId: Long): DiaryResponse
    fun deleteDaily(dailyId: Long)
    fun getDailies(pageable: Pageable): Page<DiaryResponse>
    fun getDailyByUserId(userId: Long, pageable: Pageable): Page<DiaryResponse>
    fun getDailyAndFeedback(userId: Long, dailyId: Long): DiaryAndFeedbackResponse
    fun getDiaryByMonth(userId: Long, year: Int, month: Int): List<DiaryResponse>
    fun canWriteDaily(userId: Long): Boolean
}

