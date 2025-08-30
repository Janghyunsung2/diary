package org.yojung.diary.common.config

import jakarta.annotation.PostConstruct
import java.util.TimeZone
import org.springframework.stereotype.Component

@Component
class TimeZoneConfig {
    @PostConstruct
    fun init() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"))
    }
}
