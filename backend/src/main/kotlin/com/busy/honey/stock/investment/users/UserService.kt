package com.busy.honey.stock.investment.users

import com.busy.honey.stock.investment.users.dto.CreateUserDto
import com.busy.honey.stock.investment.users.dto.UpdateUserDto
import com.busy.honey.stock.investment.users.entity.User
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class UserService {

    val repository = mutableMapOf<Long, User>()

    fun create(createUserDto: CreateUserDto): User{
        val userId = repository.size.toLong()
        val user = User(
            email = createUserDto.email,
            userName = createUserDto.username,
            password = createUserDto.password,
            createdAt = LocalDateTime.now()
        )
        repository[userId] = user
        return user
    }

    fun update(userId: Long, updateUserDto: UpdateUserDto): User{
        val user = repository[userId]!!
        user.userName = updateUserDto.username
        if(updateUserDto.password == updateUserDto.newPassword){
            user.password = updateUserDto.password
        }

        repository[userId] = user

        return user
    }

    fun delete(userId: Long): User?{
        val user = repository[userId]
        repository.remove(userId)
        return user
    }

    fun getUser(userId: Long): User? {
        return repository[userId]
    }

}