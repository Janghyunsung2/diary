package org.yojung.diary.feedback.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import org.hibernate.annotations.CreationTimestamp
import org.yojung.diary.diary.domain.Diary
import java.time.ZonedDateTime

@Entity
@Table(name = "feedbacks")
// open으로 선언한 이유는 Hibernate가 런타임에 Feedback$HibernateProxy 같은 객체를 생성해서 관리합니다.
class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feedback_id")
    var id: Long? = null

    @Column(name = "content", nullable = false)
    var content: String? = null

    @Column(name = "mode", nullable = false)
    var mode: String? = null

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "daily_id", nullable = false)
    var diary: Diary? = null

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    var createdAt: ZonedDateTime? = null;

    constructor(content: String, mode: String, diary: Diary) {
        this.content = content
        this.mode = mode
        this.diary = diary
    }

    constructor() {
        // Default constructor for JPA
    }
}