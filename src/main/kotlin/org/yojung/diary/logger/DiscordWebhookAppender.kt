package org.yojung.diary.logger

import org.apache.logging.log4j.core.Appender
import org.apache.logging.log4j.core.Layout
import org.apache.logging.log4j.core.LogEvent
import org.apache.logging.log4j.core.appender.AbstractAppender
import org.apache.logging.log4j.core.config.plugins.Plugin
import org.apache.logging.log4j.core.config.plugins.PluginAttribute
import org.apache.logging.log4j.core.config.plugins.PluginElement
import org.apache.logging.log4j.core.config.plugins.PluginFactory
import org.apache.logging.log4j.core.layout.PatternLayout
import java.io.Serializable
import java.net.HttpURLConnection
import java.net.URL

@Plugin(name = "DiscordWebhookAppender", category = "Core", elementType = Appender.ELEMENT_TYPE)
class DiscordWebhookAppender(
    name: String?,
    layout: Layout<out Serializable>?,
    private val webhookUrl: String
) : AbstractAppender(name, null, layout, false, null) {

    override fun append(event: LogEvent) {
        val message = String(layout.toByteArray(event))
        sendToDiscord(message)
    }

    private fun sendToDiscord(message: String) {
        try {
            val url = URL(webhookUrl)
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "POST"
            connection.setRequestProperty("Content-Type", "application/json")
            connection.doOutput = true
            val payload = "{\"content\": \"$message\"}"
            connection.outputStream.use { it.write(payload.toByteArray()) }
            connection.inputStream.close()
        } catch (e: Exception) {
            // 예외 무시 또는 로깅
        }
    }

    companion object {
        @JvmStatic
        @PluginFactory
        fun createAppender(
            @PluginAttribute("name") name: String?,
            @PluginAttribute("webhookUrl") webhookUrl: String?,
            @PluginElement("Layout") layout: Layout<out Serializable>?
        ): DiscordWebhookAppender? {
            val actualLayout = layout ?: PatternLayout.createDefaultLayout()
            if (name == null || webhookUrl == null) return null
            return DiscordWebhookAppender(name, actualLayout, webhookUrl)
        }
    }
}

