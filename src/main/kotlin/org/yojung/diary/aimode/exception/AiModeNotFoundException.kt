package org.yojung.diary.aimode.exception

class AiModeNotFoundException : RuntimeException {
    constructor(message: String) : super(message)
    constructor(id: Int) : super("AI 모드가 존재하지 않습니다. ID: $id")
}

