package org.yojung.diary.diary.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.yojung.diary.user.domain.User
import java.time.ZonedDateTime

@Entity
@Table(name = "dailies")
//open으로 선한한 이유는 Hibernate는 런타임에 Diary$HibernateProxy같은 객체를 생성해서 관리합니다.
class Diary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "daily_id")
    private var id: Long? = null

    @Column(name = "content", nullable = false)
    private var content: String = ""

    @Column(name = "emotion_type", nullable = false)
    private var emotionType: String? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private var user: User? = null

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private var createdAt: ZonedDateTime? = null

    @UpdateTimestamp
    private var updatedAt: ZonedDateTime? = null

    private var visibility: Boolean = true

    private var deleted: Boolean = false

    constructor(
        content: String,
        emotionType: String,
        user: User? = null,
        visibility: Boolean = true
    ) {
        this.content = content
        this.emotionType = emotionType
        if (user != null) {
            this.user = user
        };
        this.visibility = visibility
    }
    constructor() {
        // Default constructor for JPA
    }

    fun update(
        content: String? = null,
        emotionType: String? = null,
        visibility: Boolean? = null
    ) {
        if (content != null) this.content = content
        if (emotionType != null) this.emotionType = emotionType
        if (visibility != null) this.visibility = visibility
    }

    fun delete() {
        this.deleted = true
    }
    fun getId(): Long? {
        return id
    }
    fun getEmotionType(): String? {
        return emotionType
    }
    fun getCreatedAt(): ZonedDateTime? {
        return createdAt
    }
    fun getUpdatedAt(): ZonedDateTime? {
        return updatedAt
    }
    fun isDeleted(): Boolean {
        return deleted
    }

    fun getContent(): String? {
        return content
    }

    fun getVisibility(): Boolean {
        return visibility
    }

    fun getUser(): User? {
        return user
    }

}