package com.busy.honey.stock.investment.auth

import com.busy.honey.stock.investment.auth.dto.LoginDto
import com.busy.honey.stock.investment.users.UserService
import org.springframework.stereotype.Service

@Service
class AuthService (private val userService: UserService){


    fun login(loginDto: LoginDto): Boolean{
        val user = userService.repository.filter {
            (_, user) -> user.email == loginDto.email && user.password == loginDto.password
        }

        return user.isNotEmpty()
    }

    fun logout(){

    }
}