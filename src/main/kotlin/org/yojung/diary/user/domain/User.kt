package org.yojung.diary.user.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.CreationTimestamp
import java.time.ZonedDateTime

@Entity
@Table(name = "users")
class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private val id: Long = 0L;

    @Column(name = "oauth_id", nullable = false)
    private var oauthId: String = "";

    @Column(name = "provider", nullable = false)
    private var provider: String = "";

    @Column(name = "nickname", nullable = false)
    private var nickname: String = "";

    @Column(name = "password", nullable = false)
    private var password: String? = "";

    @Enumerated(EnumType.STRING)
    private var gender: Gender = Gender.UNKNOWN;

    @Column(name = "profile_image" , nullable = false)
    private var profileImage: String = "";

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private val createdAt: ZonedDateTime? = null;

    @Column(name ="last_login_at")
    private var lastLoginAt: ZonedDateTime? = null;

    private var isActive: Boolean = true;

    constructor(
        oauthId: String,
        provider: String,
        nickname: String,
        profileImage: String,
        gender : Gender
    ) {
        this.oauthId = oauthId
        this.provider = provider
        this.nickname = nickname
        this.profileImage = profileImage
        this.gender = gender
    }

    constructor(){
        // Default constructor for JPA
    }

    fun update(
        nickname: String?,
        profileImage: String?,
        gender: Gender?= Gender.UNKNOWN
    )
    {
        if (nickname != null) {
            this.nickname = nickname
        }
        if (profileImage != null) {
            this.profileImage = profileImage
        }
        if (gender != null) {
            this.gender = gender
        }
    }

    fun getId(): Long {
        return id
    }
    fun getNickname(): String {
        return nickname
    }
    fun getPassword(): String? {
        return password
    }
    fun getProfileImage(): String {
        return profileImage
    }
    fun getOauthId(): String {
        return oauthId
    }
    fun getProvider(): String {
        return provider
    }
    fun getCreatedAt(): ZonedDateTime? {
        return createdAt
    }
    fun getLastLoginAt(): ZonedDateTime? {
        return lastLoginAt
    }
    fun isActive(): Boolean {
        return isActive
    }
    fun getGender(): Gender{
        return gender;
    }

    fun lastLoginUpdate(){
        this.lastLoginAt = ZonedDateTime.now()
    }

    fun delete(){
        this.isActive = false;
    }



}