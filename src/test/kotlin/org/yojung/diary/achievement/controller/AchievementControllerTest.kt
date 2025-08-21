//package org.yojung.diary.achievement.controller
//
//import com.fasterxml.jackson.databind.ObjectMapper
//import io.kotest.core.spec.style.StringSpec
//import io.mockk.mockk
//import org.mockito.BDDMockito.given
//import org.mockito.ArgumentMatchers.any
//import org.mockito.ArgumentMatchers.eq
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
//import org.springframework.boot.test.mock.mockito.MockBean
//import org.springframework.data.domain.PageImpl
//import org.springframework.data.domain.PageRequest
//import org.springframework.data.domain.Pageable
//import org.springframework.http.MediaType
//import org.springframework.test.web.servlet.MockMvc
//import org.springframework.test.web.servlet.get
//import org.springframework.test.web.servlet.post
//import org.springframework.web.multipart.MultipartFile
//import org.yojung.diary.achievement.dto.AchievementRegisterRequest
//import org.yojung.diary.achievement.dto.AchievementResponse
//import org.yojung.diary.achievement.service.AchievementService
//import java.time.ZonedDateTime
//
//@WebMvcTest(AchievementController::class)
//class AchievementControllerTest : StringSpec() {
//
//    @Autowired
//    private lateinit var mockMvc: MockMvc
//
//    @Autowired
//    private lateinit var objectMapper: ObjectMapper
//
//    @MockBean
//    private lateinit var achievementService: AchievementService
//
//    init {
//        "POST /api/achievements - 업적 등록 요청 시 정상 처리" {
//            val mockFile: MultipartFile = mockk(relaxed = true)
//            val request = AchievementRegisterRequest(
//                "새로운 업적",
//                "멋진 새로운 업적입니다!",
//                "https://example.com/icon.png",
//                "설명",
//                1,
//                0,
//                mockFile
//            )
//            val expectedResponse = AchievementResponse(
//                "ACHIEVE_001",
//                "새로운 업적",
//                "멋진 새로운 업적입니다!",
//                "설명",
//                10,
//                1,
//                ZonedDateTime.now(),
//                "https://example.com/icon.png"
//            )
//
//            given(achievementService.registerAchievement(any()))
//                .willReturn(expectedResponse)
//
//            mockMvc.post("/api/achievements") {
//                contentType = MediaType.APPLICATION_JSON
//                content = objectMapper.writeValueAsString(request)
//            }.andExpect {
//                status { isOk() }
//                jsonPath("$.code") { value("ACHIEVE_001") }
//                jsonPath("$.name") { value("새로운 업적") }
//            }
//        }
//
//        "GET /api/achievements - 페이징 조회" {
//            val achievement1 = AchievementResponse(
//                "ACHIEVE_001", "업적 1", "첫 번째 업적", "설명", 10, 1,
//                ZonedDateTime.now(), "https://example.com/icon1.png"
//            )
//            val achievement2 = AchievementResponse(
//                "ACHIEVE_002", "업적 2", "두 번째 업적", "설명", 20, 2,
//                ZonedDateTime.now(), "https://example.com/icon2.png"
//            )
//            val achievementList = listOf(achievement1, achievement2)
//            val pageable = PageRequest.of(0, 10)
//            val expectedPage = PageImpl(achievementList, pageable, achievementList.size.toLong())
//
//            given(achievementService.getAchievements(any(Pageable::class.java)))
//                .willReturn(expectedPage)
//
//            mockMvc.get("/api/achievements?page=0&size=10") {
//                accept = MediaType.APPLICATION_JSON
//            }.andExpect {
//                status { isOk() }
//                jsonPath("$.content.length()") { value(2) }
//                jsonPath("$.content[0].name") { value("업적 1") }
//                jsonPath("$.totalPages") { value(1) }
//                jsonPath("$.totalElements") { value(2) }
//            }
//        }
//
//        "GET /api/achievements/{code} - 단일 업적 조회" {
//            val targetCode = "ACHIEVE_SINGLE"
//            val expectedResponse = AchievementResponse(
//                targetCode,
//                "단일 업적",
//                "단일 업적 설명",
//                "설명",
//                10,
//                1,
//                ZonedDateTime.now(),
//                "https://example.com/icon.png"
//            )
//
//            given(achievementService.getAchievement(eq(targetCode)))
//                .willReturn(expectedResponse)
//
//            mockMvc.get("/api/achievements/{code}", targetCode) {
//                accept = MediaType.APPLICATION_JSON
//            }.andExpect {
//                status { isOk() }
//                jsonPath("$.name") { value("단일 업적") }
//            }
//        }
//
//        "POST /api/achievements - 유효하지 않은 요청 시 BadRequest" {
//            val mockFile: MultipartFile = mockk(relaxed = true)
//            val invalidRequest = AchievementRegisterRequest(
//                "", "이름이 없는 업적", "설명 없는 업적",
//                "https://example.com/icon.png", 0, 0, mockFile
//            )
//
//            mockMvc.post("/api/achievements") {
//                contentType = MediaType.APPLICATION_JSON
//                content = objectMapper.writeValueAsString(invalidRequest)
//            }.andExpect {
//                status { isBadRequest() }
//            }
//        }
//    }
//}
