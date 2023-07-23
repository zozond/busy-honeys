package com.busy.honey.stock.investment.users

import com.busy.honey.stock.investment.users.dto.CreateUserDto
import com.busy.honey.stock.investment.users.dto.UpdateUserDto
import com.busy.honey.stock.investment.users.entity.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class UserService (@Autowired val userRepository: UserRepository){

    fun create(createUserDto: CreateUserDto): User{
        return userRepository.save( User(
            userId = null,
            email = createUserDto.email,
            username = createUserDto.username,
            password = createUserDto.password,
            createdAt = LocalDateTime.now()
        ))
    }

    fun update(userId: Long, updateUserDto: UpdateUserDto): User{
        val optionalUser = userRepository.findById(userId)
        if(optionalUser.isEmpty){
            throw Exception("유저가 생성되지 않았는데 업데이트 한 경우 ")
        }

        if(updateUserDto.password != updateUserDto.newPassword){
            throw Exception("Not equals password and password confirm")
        }
        val user = optionalUser.get()
        user.username = updateUserDto.username;
        user.password = updateUserDto.password;
        userRepository.save(user)
        return user

    }

    fun delete(userId: Long){
        this.userRepository.deleteById(userId)
    }

    fun findUser(userId: Long): User? {
        return userRepository.findById(userId).get()
    }

    fun findUser(email: String, password: String): User? {
        val userList = userRepository.findByEmailAndPassword(email, password)
        if (userList.isEmpty()){
            return null
        }

        return userList[0]
    }
}