package org.yojung.diary.diary.ai.exception

class GptMessageParsingException(content: String) : RuntimeException(content + "Failed to parse GPT message") {
}