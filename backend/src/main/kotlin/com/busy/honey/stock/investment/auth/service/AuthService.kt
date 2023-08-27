package com.busy.honey.stock.investment.auth.service

import com.busy.honey.stock.investment.auth.dto.LoginDto
import com.busy.honey.stock.investment.users.service.UserService
import org.springframework.stereotype.Service

@Service
class AuthService (private val userService: UserService){



    fun login(loginDto: LoginDto): Boolean{
        return userService.findUser(email=loginDto.email, password = loginDto.password) != null
    }

    fun logout(){

    }
}