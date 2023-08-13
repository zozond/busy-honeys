package com.busy.honey.stock.investment.auth.controller;

import com.busy.honey.stock.investment.auth.service.AuthService
import com.busy.honey.stock.investment.response.RestApiResponse
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/logout")
@CrossOrigin(origins = ["*"], allowedHeaders = ["*"])
class LogoutController (private val authService: AuthService){

    @GetMapping
    fun logout(): RestApiResponse {
        return RestApiResponse(
            status = "OK",
            description = "logout",
            data = null
        )
    }
}
