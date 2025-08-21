package org.yojung.diary.userachievement.exception

class UserAchievementNotFoundException : RuntimeException {
    constructor(message: String) : super(message)
    constructor(id: Long) : super("유저 업적이 존재하지 않습니다. ID: $id")
}

