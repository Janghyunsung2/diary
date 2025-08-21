package org.yojung.diary.diary.exception

class DiaryNotFoundException : RuntimeException {
    constructor(message: String) : super(message)
    constructor(id: Long) : super("일기가 존재하지 않습니다. ID: $id")
}

