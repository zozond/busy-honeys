package com.busy.honey.stock.investment.auth.controller

import com.busy.honey.stock.investment.accounts.service.JwtService
import com.busy.honey.stock.investment.auth.dto.LoginDto
import com.busy.honey.stock.investment.auth.service.AuthService
import com.busy.honey.stock.investment.response.RestApiResponse
import com.busy.honey.stock.investment.users.service.UserService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/login")
@CrossOrigin(origins = ["*"], allowedHeaders = ["*"])
class LoginController (private val authService: AuthService,
                       private val userService: UserService,
                       private val jwtService: JwtService){

    @PostMapping
    fun login(@RequestBody loginDto: LoginDto): RestApiResponse {
        val status = if (this.authService.login(loginDto)) "OK" else "Failure"
        val token = jwtService.createToken(loginDto.email + loginDto.password)
        var userId = 0L;
        if (userService.findUser(loginDto.email, loginDto.password) != null){
             userId = userService.findUser(loginDto.email, loginDto.password)!!.userId!!
        }

        val data = mutableMapOf<Any, Any>()
        data.put("token", token)
        data.put("userId", userId)
        return RestApiResponse(
            status = status,
            description = "login",
            data = data
        )
    }

}