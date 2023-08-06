package com.busy.honey.stock.investment.construct

import com.busy.honey.stock.investment.users.UserService
import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Service

@Service
class PostConstructService(val userService: UserService) {

    @PostConstruct
    fun initialize() {
        // 관리자
        userService.createAdminAndBot(email = "admin@admin.com", username = "admin", password = "admin", userType = "관리자")

        // 봇
        userService.createAdminAndBot(email = "bot1@admin.com", username = "bot1", password = "bot1", userType = "봇")
        userService.createAdminAndBot(email = "bot2@admin.com", username = "bot2", password = "bot2", userType = "봇")
        userService.createAdminAndBot(email = "bot3@admin.com", username = "bot3", password = "bot3", userType = "봇")
        userService.createAdminAndBot(email = "bot4@admin.com", username = "bot4", password = "bot4", userType = "봇")
        userService.createAdminAndBot(email = "bot5@admin.com", username = "bot5", password = "bot5", userType = "봇")
        userService.createAdminAndBot(email = "bot6@admin.com", username = "bot6", password = "bot6", userType = "봇")
        userService.createAdminAndBot(email = "bot7@admin.com", username = "bot7", password = "bot7", userType = "봇")
        userService.createAdminAndBot(email = "bot8@admin.com", username = "bot8", password = "bot8", userType = "봇")
        userService.createAdminAndBot(email = "bot9@admin.com", username = "bot9", password = "bot9", userType = "봇")
    }

}