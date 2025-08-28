package org.yojung.diary.common.security.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import org.yojung.diary.common.security.CustomUserDetailsService
import org.yojung.diary.common.security.JwtTokenProvider
import org.yojung.diary.common.security.service.AuthService
import org.yojung.diary.common.security.dto.OauthLoginRequest
import org.yojung.diary.common.security.dto.TokenResponse
import org.yojung.diary.user.service.UserService

@WebMvcTest(AuthController::class)
class AuthControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockBean
    private lateinit var authService: AuthService

    @MockBean
    private lateinit var userService: UserService

    @MockBean
    private lateinit var jwtTokenProvider: JwtTokenProvider

    @MockBean
    private lateinit var customUserDetailsService: CustomUserDetailsService

    @Disabled
    @Test
    @DisplayName("OAuth 로그인 성공 테스트")
    fun oauth_login_should_return_token_response() {
        // given
        val request = OauthLoginRequest(
            provider = "kakao",
            providerId = "1234567890"
        )

        val expectedResponse = TokenResponse(
            accessToken = "mockAccessToken",
            refreshToken = "mockRefreshToken",
            firstLogin = true
        )

        given(authService.oauthLogin(any()))
            .willReturn(expectedResponse)
        // when & then
        mockMvc.post("/api/auth/oauth") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(request)
        }
            .andExpect {
                status { isOk() }
                jsonPath("$.accessToken") { value("mockAccessToken") }
                jsonPath("$.refreshToken") { value("mockRefreshToken") }
                jsonPath("$.firstLogin") { value(true) }
            }
    }
}