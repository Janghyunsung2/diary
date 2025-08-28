package org.yojung.diary.credittransaction.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.yojung.diary.common.security.CustomUserDetails
import org.yojung.diary.credittransaction.service.CreditTransactionService

@RestController
@RequestMapping("/api/credit-transaction")
class CreditTransactionController (
    @Autowired
    private val creditTransactionService: CreditTransactionService
){

    @GetMapping("/me")
    fun getMyCreditTransaction(@AuthenticationPrincipal user: CustomUserDetails): ResponseEntity<Int> {
        val response = creditTransactionService.getCreditTransactionByMe(user.getId())
        return ResponseEntity.ok(response)
    }

}