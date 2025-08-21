package org.yojung.diary.feedback.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.yojung.diary.feedback.domain.Feedback

interface FeedbackRepository : JpaRepository<Feedback, Long>{
    fun findByDiaryId(diaryId: Long): Feedback?
}
