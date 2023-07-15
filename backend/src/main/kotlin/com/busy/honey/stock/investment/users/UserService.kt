package com.busy.honey.stock.investment.users

import com.busy.honey.stock.investment.users.dto.CreateUserDto
import com.busy.honey.stock.investment.users.dto.UpdateUserDto
import com.busy.honey.stock.investment.users.entity.User
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import kotlin.math.E

@Service
class UserService {

    val repository = mutableMapOf<Long, User>()

    fun create(createUserDto: CreateUserDto): User{
        val userId = repository.size.toLong()
        val user = User(
            userId = userId,
            email = createUserDto.email,
            userName = createUserDto.username,
            password = createUserDto.password,
            createdAt = LocalDateTime.now()
        )
        repository[userId] = user
        return user
    }

    fun update(userId: Long, updateUserDto: UpdateUserDto): User{
        if (repository[userId] == null){
            throw Exception("유저가 생성되지 않았는데 업데이트 한 경우 ")
        }

        val user = repository[userId]!!
        if(updateUserDto.password == updateUserDto.newPassword){
            val newUser = User(
                userId = userId,
                email = user.email,
                userName = updateUserDto.username,
                password = updateUserDto.password,
                createdAt = LocalDateTime.now()
            )
            repository[userId] = newUser
            return newUser
        }

        throw Exception("Not equals password and password confirm")
    }

    fun delete(userId: Long): User?{
        val user = repository[userId]
        repository.remove(userId)
        return user
    }

    fun findUser(userId: Long): User? {
        return repository[userId]
    }

}