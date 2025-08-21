package org.yojung.diary.common.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.yojung.diary.common.security.AdminAuthInterceptor

@Configuration
class WebConfig(
    private val adminAuthInterceptor: AdminAuthInterceptor
) : WebMvcConfigurer {

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(adminAuthInterceptor)
            .addPathPatterns("/admin-server/**")
            .excludePathPatterns(
                "/admin-server/login",               // 로그인 페이지 제외
                "/admin-server/logout",              // 로그아웃 제외
                "/css/**", "/js/**", "/images/**"   // 정적 리소스 제외
            )    }
}