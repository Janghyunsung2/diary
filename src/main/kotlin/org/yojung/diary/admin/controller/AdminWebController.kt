package org.yojung.diary.admin.controller

import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.yojung.diary.admin.service.AdminService
import org.yojung.diary.aimode.service.AiModeService
import org.yojung.diary.achievement.service.AchievementService
import org.yojung.diary.credittransaction.service.CreditTransactionService
import org.yojung.diary.user.service.UserService
import org.yojung.diary.diary.service.DiaryService
import jakarta.servlet.http.HttpSession
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.context.HttpSessionSecurityContextRepository
import org.yojung.diary.achievement.dto.AchievementRegisterRequest
import org.yojung.diary.achievement.dto.AchievementUpdateRequest
import org.yojung.diary.aimode.dto.AiModeRegisterRequest
import org.yojung.diary.aimode.dto.AiModeUpdateRequest
import org.yojung.diary.credittransaction.dto.CreditTransactionRegisterRequest

@Controller
@RequestMapping("/admin-server")
class AdminWebController(
    private val adminService: AdminService,
    private val aiModeService: AiModeService,
    private val achievementService: AchievementService,
    private val creditTransactionService: CreditTransactionService,
    private val userService: UserService,
    private val diaryService: DiaryService
) {

    @GetMapping("/login")
    fun loginPage(@RequestParam(required = false) error: String?, model: Model): String {
        if (error != null) {
            model.addAttribute("error", "이메일 또는 비밀번호가 올바르지 않습니다.")
        }
        return "admin-login"
    }

    @PostMapping("/login")
    fun login(
        @RequestParam email: String,
        @RequestParam password: String,
        session: HttpSession
    ): String {
        println("로그인 시도: email=$email")
        return try {
            val isAuthenticated = adminService.authenticateAdmin(email, password)
            if (!isAuthenticated) {
                return "redirect:/admin-server/login?error=true"
            }

            println("인증 성공: $email")
            session.setAttribute("adminEmail", email)
            session.setAttribute("isAdmin", true)

            val auth = UsernamePasswordAuthenticationToken(
                email,
                null,
                listOf(SimpleGrantedAuthority("ROLE_ADMIN"))
            )
            SecurityContextHolder.getContext().authentication = auth
            session.setAttribute(
                HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                SecurityContextHolder.getContext()
            )

            "redirect:/admin-server/admins"
        } catch (e: Exception) {
            println("로그인 예외: ${e.message}")
            "redirect:/admin-server/login?error=true"
        }
    }


    @GetMapping("/users")
    fun usersPage(session: HttpSession, pageable: Pageable, model: Model): String {
        if (!isAdminLoggedIn(session)) {
            return "redirect:/admin-server/login"
        }
        val users = userService.getUsers(pageable)
        model.addAttribute("users", users)
        return "users"
    }

    @GetMapping("/admins")
    fun adminsPage(session: HttpSession, model: Model, pageable: Pageable): String {
        if (!isAdminLoggedIn(session)) {
            return "redirect:/admin-server/login"
        }
        val admins = adminService.getAdmins(pageable)
        model.addAttribute("adminPage", admins)
        return "admins"
    }

    @GetMapping("/ai-modes")
    fun aiModesPage(session: HttpSession, pageable: Pageable, model: Model): String {
        if (!isAdminLoggedIn(session)) {
            return "redirect:/admin-server/login"
        }
        val aiModes = aiModeService.getAiModes(pageable)
        model.addAttribute("aiModes", aiModes)
        return "ai-modes"
    }

    @GetMapping("/ai-modes/create")
    fun aiModeCreateForm(session: HttpSession, model: Model): String {
        if (!isAdminLoggedIn(session)) {
            return "redirect:/admin-server/login"
        }
        model.addAttribute("action", "create")

        return "ai-mode-form"
    }

    @PostMapping("/ai-modes/create")
    fun createAiMode(
        session: HttpSession,
        @ModelAttribute request: AiModeRegisterRequest
    ): String {
        if (!isAdminLoggedIn(session)) {
            return "redirect:/admin-server/login"
        }
        try {
            aiModeService.createAiMode(request)
        } catch (e: Exception) {
            e.printStackTrace()
            return "redirect:/admin-server/ai-modes/create?error=true"
        }
        return "redirect:/admin-server/ai-modes"
    }
    @PostMapping("/ai-modes/update")
    fun updateAiMode(
        session: HttpSession,
        @ModelAttribute request: AiModeUpdateRequest
    ): String {
        if (!isAdminLoggedIn(session)) {
            return "redirect:/admin-server/login"
        }
        try {
            aiModeService.updateAiMode(request.mode, request)
        } catch (e: Exception) {
            e.printStackTrace()
            return "redirect:/admin-server/ai-modes"
        }
        return "redirect:/admin-server/ai-modes"
    }

    @GetMapping("/ai-modes/update")
    fun aiModeUpdateForm(session: HttpSession, @RequestParam aiModeId: Int, model: Model): String {
        if (!isAdminLoggedIn(session)) {
            return "redirect:/admin-server/login"
        }
        val aiMode = aiModeService.getAiMode(aiModeId)
        model.addAttribute("action", "update")
        model.addAttribute("aiMode", aiMode)
        return "ai-mode-form"
    }

    @GetMapping("/achievements")
    fun achievementsPage(session: HttpSession, pageable: Pageable, model: Model): String {
        if (!isAdminLoggedIn(session)) {
            return "redirect:/admin-server/login"
        }
        val achievements = achievementService.getAchievements(pageable)
        model.addAttribute("achievements", achievements)
        return "achievements"
    }

    @GetMapping("/achievements/form")
    fun achievementCreateForm(session: HttpSession, model: Model): String {
        if (!isAdminLoggedIn(session)) {
            return "redirect:/admin-server/login"
        }
        model.addAttribute("action", "create")
        return "achievement-form"
    }


    @GetMapping("/achievements/form/{code}")
    fun achievementUpdateForm(session: HttpSession, @PathVariable code: String, model: Model): String {
        if (!isAdminLoggedIn(session)) {
            return "redirect:/admin-server/login"
        }
        model.addAttribute("action", "update")
        val achievement = achievementService.getAchievement(code)
        model.addAttribute("achievement", achievement)
        return "achievement-form"
    }

    @PostMapping("/achievements/create")
    fun createAchievement(session: HttpSession, @ModelAttribute request: AchievementRegisterRequest): String {
        if (!isAdminLoggedIn(session)) {
            return "redirect:/admin-server/login"
        }
        try {
            achievementService.registerAchievement(request)
        } catch (e: Exception) {
            e.printStackTrace()
            return "redirect:/admin-server/achievements/form?error=true"
        }
        return "redirect:/admin-server/achievements"
    }

    @PostMapping("/achievements/update")
    fun updateAchievement(
        session: HttpSession,
        @ModelAttribute request: AchievementUpdateRequest
    ): String {
        if (!isAdminLoggedIn(session)) {
            return "redirect:/admin-server/login"
        }
        try {
            achievementService.updateAchievement(request.code, request)
        } catch (e: Exception) {
            e.printStackTrace()
            return "redirect:/admin-server/achievements/form/${request.code}?error=true"
        }
        return "redirect:/admin-server/achievements"
    }

    @PostMapping("/achievements/delete/{code}")
    fun deleteAchievement(session: HttpSession, @PathVariable code: String): String {
        if (!isAdminLoggedIn(session)) {
            return "redirect:/admin-server/login"
        }
        achievementService.deleteAchievement(code)
        return "redirect:/admin-server/achievements"
    }

    @GetMapping("/credit-transactions")
    fun creditTransactionsPage(session: HttpSession, pageable: Pageable, model: Model): String {
        if (!isAdminLoggedIn(session)) {
            return "redirect:/admin-server/login"
        }
        val transactions = creditTransactionService.getTransactions(pageable)
        model.addAttribute("creditTransactions", transactions)
        return "credit-transactions"
    }

    @PostMapping("/credit-transactions")
    fun createCreditTransaction(
        session: HttpSession,
        @ModelAttribute request: CreditTransactionRegisterRequest
    ): String {
        if (!isAdminLoggedIn(session)) {
            return "redirect:/admin-server/login"
        }
        try {
            creditTransactionService.registerTransaction(request)
        } catch (e: Exception) {
            e.printStackTrace()
            return "redirect:/admin-server/credit-transactions/create?error=true"
        }
        return "redirect:/admin-server/credit-transactions"
    }

    @GetMapping("/credit-transactions/create")
    fun creditTransactionForm(session: HttpSession, model: Model): String {
        if (!isAdminLoggedIn(session)) {
            return "redirect:/admin-server/login"
        }
        model.addAttribute("action", "create")
        return "credit-transaction-form"
    }

    @GetMapping("/dailies")
    fun dailiesPage(session: HttpSession, pageable: Pageable, model: Model): String {
        if (!isAdminLoggedIn(session)) {
            return "redirect:/admin-server/login"
        }
        val dailies = diaryService.getDailies(pageable)
        model.addAttribute("dailies", dailies)
        return "dailies"
    }

    @GetMapping("/dailies/{dailyId}")
    fun dailyDetail(session: HttpSession, @PathVariable("dailyId") dailyId: Long, model: Model): String {
        if (!isAdminLoggedIn(session)) {
            return "redirect:/admin-server/login"
        }
        val daily = diaryService.getDaily(dailyId)
        model.addAttribute("daily", daily)
        return "daily-detail"
    }

    @GetMapping("/logout")
    fun logout(session: HttpSession): String {
        session.invalidate()
        return "redirect:/admin-server/login"
    }

    private fun isAdminLoggedIn(session: HttpSession): Boolean {
        return session.getAttribute("isAdmin") == true
    }
}
