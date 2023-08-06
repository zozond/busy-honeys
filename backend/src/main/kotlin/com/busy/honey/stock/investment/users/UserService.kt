package com.busy.honey.stock.investment.users

import com.busy.honey.stock.investment.accounts.AccountsRepository
import com.busy.honey.stock.investment.accounts.entity.Account
import com.busy.honey.stock.investment.users.dto.CreateUserDto
import com.busy.honey.stock.investment.users.dto.UpdateUserDto
import com.busy.honey.stock.investment.users.entity.User
import com.busy.honey.stock.investment.users.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class UserService (val userRepository: UserRepository,
                   val accountsRepository: AccountsRepository){

    
    @Transactional
    fun createAdminAndBot(createUserDto: CreateUserDto): User{
        val account = accountsRepository.save(Account(accountId = null, money = 1000000))
        return userRepository.save( User(
            userId = null,
            email = createUserDto.email,
            username = createUserDto.username,
            password = createUserDto.password,
            accountId = account.accountId!!,
            userType = "일반유저",
            createdAt = LocalDateTime.now()
        ))
    }

    @Transactional
    fun createAdminAndBot(email: String, username:String, password:String, userType: String): User{
        val account = accountsRepository.save(Account(accountId = null, money = 1000000))
        return userRepository.save( User(
            userId = null,
            email = email,
            username = username,
            password = password,
            accountId = account.accountId!!,
            userType = userType,
            createdAt = LocalDateTime.now()
        ))
    }


    fun update(userId: Long, updateUserDto: UpdateUserDto): User{
        val optionalUser = userRepository.findById(userId)
        if(optionalUser.isEmpty){
            throw Exception("유저가 생성되지 않았는데 업데이트 한 경우 ")
        }

        if(updateUserDto.password != updateUserDto.newPassword){
            throw Exception("새로운 패스워드와 컨펌된 패스워드가 같지 않음.")
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
        val optionalUser = userRepository.findById(userId)
        if(optionalUser.isEmpty)
            return null

        return optionalUser.get()
    }

    fun findUser(email: String, password: String): User? {
        val userList = userRepository.findByEmailAndPassword(email, password)
        if (userList.isEmpty()){
            return null
        }
        // 계좌 내용도 같이 보여줘야 함

        return userList[0]
    }
    
    fun findBotList(): List<User>{
        return userRepository.findByUserType("봇")
    }
}