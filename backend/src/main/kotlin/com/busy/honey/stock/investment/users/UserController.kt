package com.busy.honey.stock.investment.users

import com.busy.honey.stock.investment.response.RestApiResponse
import com.busy.honey.stock.investment.users.dto.CreateUserDto
import com.busy.honey.stock.investment.users.dto.UpdateUserDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController (private val userService: UserService){

    @GetMapping("/{userId}")
    fun getUser(@PathVariable("userId") userId: Long): RestApiResponse{
        val user = this.userService.findUser(userId)
        val data = mutableMapOf<Any, Any>()
        data["email"] = user!!.email
        data["userName"] = user.username
        data["totalEarningRate"] = 0.0
        data["accountPrice"] = 0
        data["stocksInfo"] = intArrayOf()

        return RestApiResponse(
            status = "OK",
            description = "success",
            data = data
        )
    }

    @PostMapping
    fun createUser(@RequestBody createUserDto: CreateUserDto): RestApiResponse {
        val user = this.userService.create(createUserDto)
        val data = mutableMapOf<Any, Any>()
        data["createdAt"] = user.createdAt.toString()
        data["email"] = user.email
        data["userName"] = user.username
        data["userId"] = user.userId!!
        data["accountId"] = user.accountId

        return RestApiResponse(
            status = "OK",
            description = "success",
            data = data
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