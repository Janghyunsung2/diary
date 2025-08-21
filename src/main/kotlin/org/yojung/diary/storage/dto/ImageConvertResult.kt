package org.yojung.diary.storage.dto

data class ImageConvertResult(
    val converted: Boolean,
    var bytes: ByteArray,
    val extension: String
)