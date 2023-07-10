package com.busy.honey.stock.investment.auth

import com.busy.honey.stock.investment.auth.dto.LoginDto
import com.busy.honey.stock.investment.response.RestApiResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/login")
class LoginController (private val authService: AuthService){

    @PostMapping
    fun login(@RequestBody loginDto: LoginDto): RestApiResponse {
        val status = if (this.authService.login(loginDto)) "OK" else "Failure"
        return RestApiResponse(
            status = status,
            description = "login",
            data = null
        )
    }
}