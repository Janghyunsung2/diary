package org.yojung.diary.diary.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.yojung.diary.diary.domain.Diary
import org.yojung.diary.diary.dto.DiaryResponse
import java.time.LocalDate
import java.time.ZonedDateTime

interface DiaryRepository : JpaRepository<Diary, Long> {
    @Query("""
        select new org.yojung.diary.diary.dto.DiaryResponse(
            d.id, d.content, d.emotionType, d.user.id, d.createdAt, d.updatedAt, d.visibility, d.deleted
        )
        from Diary d
        where d.user.id = :userId and d.deleted = false
    """)
    fun findByUserId(
        @Param("userId") userId: Long,
        pageable: Pageable
    ): Page<DiaryResponse>

    @Query("""
        select new org.yojung.diary.diary.dto.DiaryResponse(
            d.id, d.content, d.emotionType, d.user.id, d.createdAt, d.updatedAt, d.visibility, d.deleted
        )
        from Diary d
        where d.user.id = :userId and year(d.createdAt) = :year and month(d.createdAt) = :month and d.deleted = false
    """)
    fun findByYearAndMonth(
        @Param("userId") userId: Long,
        @Param("year") year: Int,
        @Param("month") month: Int
    ): List<DiaryResponse>

    fun existsByUserIdAndCreatedAt(userId: Long, createdAt: ZonedDateTime): Boolean
}