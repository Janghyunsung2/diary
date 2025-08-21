package org.yojung.diary.feedback.exception

class FeedbackNotFoundException : RuntimeException {
    constructor(message: String) : super(message)
    constructor(id: Long) : super("피드백이 존재하지 않습니다. ID: $id")
}

