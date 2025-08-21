package org.yojung.diary.achievement.exception

class AchievementNotFoundException(name: String?) : RuntimeException(
    "Achievement with code '$name' not found."
)
