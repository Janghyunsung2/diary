package org.yojung.diary.user.exception

class UserNotFoundException : RuntimeException {
    constructor(message: String) : super(message)
    constructor(id: Long) : super("유저가 존재하지 않습니다. ID: $id")
}

