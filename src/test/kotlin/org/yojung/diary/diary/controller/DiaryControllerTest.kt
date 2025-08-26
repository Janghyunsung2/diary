package org.yojung.diary.diary.controller

import com.fasterxml.jackson.databind.ObjectMapper
import io.kotest.core.spec.style.StringSpec
import io.kotest.core.test.TestCase
import io.kotest.extensions.spring.SpringExtension
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.FilterType
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.http.MediaType
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.yojung.diary.common.security.CustomUserDetails
import org.yojung.diary.common.security.JwtAuthenticationFilter
import org.yojung.diary.diary.dto.*
import org.yojung.diary.diary.service.DiaryService

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(
    controllers = [DiaryController::class],
    excludeAutoConfiguration = [
        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration::class,
        org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration::class
    ],
    excludeFilters = [
        ComponentScan.Filter(
            type = FilterType.ASSIGNABLE_TYPE,
            classes = [JwtAuthenticationFilter::class]
        )
    ]
)
@TestPropertySource(
    properties = [
        "jwt.secret=0123456789abcdef0123456789abcdef0123456789abcdef0123456789abcdef",
        "jwt.expiration=3600000"
    ]
)
class DiaryControllerTest(
    @Autowired private val mockMvc: MockMvc,
    @Autowired private val objectMapper: ObjectMapper,
) : StringSpec() {

    @MockitoBean
    lateinit var diaryService: DiaryService

    override fun extensions() = listOf(SpringExtension)



    override suspend fun beforeEach(testCase: TestCase) {
        super.beforeEach(testCase)
        val principal = CustomUserDetails(
            id = 1L,
            email = "",
            password = "",
            role = "ROLE_USER",
            )
        val auth = UsernamePasswordAuthenticationToken(principal, null, null)
        SecurityContextHolder.getContext().authentication = auth
    }

    override suspend fun afterEach(testCase: TestCase, result: io.kotest.core.test.TestResult) {
        SecurityContextHolder.clearContext()
        super.afterEach(testCase, result)
    }

    init {
        "일기 생성 성공" {
            val req = DiaryRegisterRequest("테스터", "내용", "HAPPY", true, 1, false)
            val res = DiaryResponse(1L, req.content, req.emotionType, 1L, null, null, req.visibility, false)
            given(diaryService.createDaily(1L, req)).willReturn(res)

            mockMvc.perform(
                post("/api/dailies")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(req))
            )
                .andExpect(status().isOk)
                .andExpect(content().json(objectMapper.writeValueAsString(res)))
        }

        "일기 수정 성공" {
            val dailyId = 100L
            val req = DiaryUpdateRequest("수정내용", "SAD", false)
            val res = DiaryResponse(dailyId, req.content, req.emotionType, 1L, null, null, req.visibility ?: true, false)
            given(diaryService.updateDaily(dailyId, req)).willReturn(res)

            mockMvc.perform(
                put("/api/dailies/{id}", dailyId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(req))
            )
                .andExpect(status().isOk)
                .andExpect(content().json(objectMapper.writeValueAsString(res)))
        }

        "일기 삭제 성공" {
            mockMvc.perform(delete("/api/dailies/{id}", 100L))
                .andExpect(status().isNoContent)
        }

        "일기 목록 조회 성공" {
            val list = listOf(
                DiaryResponse(1, "일기1", "HAPPY", 1, null, null, true, false),
                DiaryResponse(2, "일기2", "SAD", 1, null, null, true, false)
            )
            given(diaryService.getDailyByUserId(1L, Pageable.unpaged()))
                .willReturn(PageImpl(list))

            mockMvc.perform(get("/api/dailies"))
                .andExpect(status().isOk)
        }
    }
}