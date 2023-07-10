package com.busy.honey.stock.investment.auth;

import com.busy.honey.stock.investment.response.RestApiResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/logout")
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
