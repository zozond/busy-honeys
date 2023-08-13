package com.busy.honey.stock.investment.auth.controller

import com.busy.honey.stock.investment.auth.dto.LoginDto
import com.busy.honey.stock.investment.auth.service.AuthService
import com.busy.honey.stock.investment.response.RestApiResponse
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/login")
@CrossOrigin(origins = ["*"], allowedHeaders = ["*"])
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