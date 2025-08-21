package org.yojung.diary.admin.controller

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.yojung.diary.admin.dto.AdminResponse
import org.yojung.diary.admin.service.AdminService

@RestController
@RequestMapping("/api/admins")
class AdminController(
    private val adminService: AdminService
) {
    @GetMapping
    fun getAdmins(pageable: Pageable): ResponseEntity<Page<AdminResponse>> {
        return ResponseEntity.ok(adminService.getAdmins(pageable))
    }
}

