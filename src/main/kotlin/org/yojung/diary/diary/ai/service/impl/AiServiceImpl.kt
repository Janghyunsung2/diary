package org.yojung.diary.diary.ai.service.impl

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.annotation.PostConstruct
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatusCode
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.yojung.diary.diary.ai.exception.GptMessageParsingException
import org.yojung.diary.diary.ai.service.AiService
import org.yojung.diary.feedback.dto.FeedbackRequest
import org.yojung.diary.feedback.dto.FeedbackResponse
import reactor.core.publisher.Mono

@Service
class AiServiceImpl(
    private val objectMapper: ObjectMapper
) : AiService {

    private val logger = LoggerFactory.getLogger(AiServiceImpl::class.java)

    @Value("\${openai.api-key}")
    private lateinit var apiKey: String

    @Value("\${openai.model.gpt-3.5-turbo}")
    private lateinit var defaultModel: String

    private lateinit var webClient: WebClient

    companion object {
        private const val API_URL = "https://api.openai.com/v1/chat/completions"
    }

    @PostConstruct
    fun init() {
        this.webClient = buildWebClient(apiKey)
        logger.info("✅ apiKey: {}", apiKey)
        logger.info("✅ model: {}", defaultModel)
    }

    private fun buildWebClient(apiKey: String): WebClient {
        return WebClient.builder()
            .baseUrl(API_URL)
            .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer $apiKey")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build()
    }

    override fun getFeedback(feedbackRequest: FeedbackRequest): FeedbackResponse {
        val model = if (feedbackRequest.isUseCredit) "gpt-4o" else "gpt-3.5-turbo"

        delayForRateLimit()

        val requestBody = buildRequestBody(feedbackRequest, model)

        val responseMap = webClient.post()
            .bodyValue(requestBody)
            .retrieve()
            .onStatus(HttpStatusCode::isError) { clientResponse ->
                clientResponse.bodyToMono(String::class.java)
                    .flatMap { errorBody ->
                        logger.error("❌ GPT API 오류: {}", errorBody)
                        Mono.error(RuntimeException("GPT 호출 실패"))
                    }
            }
            .bodyToMono(Map::class.java)
            .block()!!

        val feedback = extractFeedbackContent(responseMap)
        val parsed = parseFeedbackJson(feedback, feedbackRequest)

        return buildFeedbackResponse(feedbackRequest, parsed)
    }

    private fun delayForRateLimit() {
        try {
            Thread.sleep(5000)
        } catch (e: InterruptedException) {
            Thread.currentThread().interrupt()
        }
    }

    private fun buildRequestBody(feedbackRequest: FeedbackRequest, model: String): Map<String, Any> {
        val prompt = buildPrompt(feedbackRequest)
        return mapOf(
            "model" to model,
            "messages" to listOf(
                mapOf("role" to "system", "content" to prompt),
                mapOf("role" to "user", "content" to "${feedbackRequest.nickname}님의 오늘 일기: \"${feedbackRequest.content}\"")
            ),
            "temperature" to 0.7
        )
    }
    private fun buildPrompt(feedbackRequest: FeedbackRequest): String {
        return """
        ${feedbackRequest.prompt}

        Please read the user's diary entry carefully and provide a sincere, thoughtful, and supportive response. 
        The feedback should help the user reflect on their day and feel understood. 
        Do not include any scores or numerical evaluations. 
        Only provide natural, well-written feedback. 

        All responses must be written in Korean.
    """.trimIndent()
    }


    @Suppress("UNCHECKED_CAST")
    private fun extractFeedbackContent(responseMap: Map<*, *>): String {
        val choices = responseMap["choices"] as List<Map<String, Any>>
        val message = choices[0]["message"] as Map<String, Any>
        return message["content"] as String
    }

    private fun parseFeedbackJson(feedback: String, feedbackRequest: FeedbackRequest): Map<String, Any> {
        return try {
            var jsonPart = feedback
            if (!feedback.trim().startsWith("{")) {
                val start = feedback.indexOf('{')
                val end = feedback.lastIndexOf('}')
                if (start != -1 && end != -1 && end > start) {
                    jsonPart = feedback.substring(start, end + 1)
                }
            }
            objectMapper.readValue(jsonPart, object : TypeReference<Map<String, Any>>() {})
        } catch (e: JsonProcessingException) {
            logger.error("GPT 응답 파싱 실패. 원본 응답: {}", feedback, e)
            throw GptMessageParsingException(feedbackRequest.content)
        }
    }

    private fun buildFeedbackResponse(request: FeedbackRequest, map: Map<String, Any>): FeedbackResponse {
        return FeedbackResponse(
            content = map["feedback"] as String,
            mode = "",
            imageUrl = null
        )
    }
}
