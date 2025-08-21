package org.yojung.diary.user.domain

enum class Gender {

    MALE("남성"),
    FEMALE("여성"),
    UNKNOWN("미지정");

    lateinit var description: String;

    constructor(description: String) {
        this.description = description
    }
    constructor() {
        // Default constructor for JPA
    }



}