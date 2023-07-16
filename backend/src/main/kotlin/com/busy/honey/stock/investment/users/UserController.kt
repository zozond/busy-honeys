package com.busy.honey.stock.investment.users

import com.busy.honey.stock.investment.response.RestApiResponse
import com.busy.honey.stock.investment.users.dto.CreateUserDto
import com.busy.honey.stock.investment.users.dto.UpdateUserDto
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController (private val userService: UserService){

    @GetMapping("/{userId}")
    fun getUser(@PathVariable("userId") userId: Long): RestApiResponse{
        val user = this.userService.findUser(userId)
        val data = mutableMapOf<Any, Any>()
        return RestApiResponse(
            status = "OK",
            description = "success",
            data = data
        )
    }

    @PostMapping
    fun createUser(@RequestBody createUserDto: CreateUserDto): RestApiResponse {
        val user = this.userService.create(createUserDto)
        return RestApiResponse(
            status = "OK",
            description = "success",
            data = null
        )
    }

    @PutMapping("/{userId}")
    fun updateUserInfo(@PathVariable("userId") userId: Long,
                       @RequestBody updateUserDto: UpdateUserDto): RestApiResponse{
        val user = this.userService.update(userId, updateUserDto)
        return RestApiResponse(
            status = "OK",
            description = "success",
            data = null
        )
    }

    @DeleteMapping("/{userId}")
    fun deleteUser(@PathVariable("userId") userId: Long): RestApiResponse{
        this.userService.delete(userId)
        return RestApiResponse(
            status = "OK",
            description = "success",
            data = null
        )
    }

}