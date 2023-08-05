package com.busy.honey.stock.investment.users

import com.busy.honey.stock.investment.accounts.AccountsRepository
import com.busy.honey.stock.investment.accounts.entity.Account
import com.busy.honey.stock.investment.users.dto.CreateUserDto
import com.busy.honey.stock.investment.users.dto.UpdateUserDto
import com.busy.honey.stock.investment.users.entity.User
import jakarta.annotation.PostConstruct
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import kotlin.jvm.optionals.getOrNull

@Service
class UserService (val userRepository: UserRepository,
                   val accountsRepository: AccountsRepository){
    @PostConstruct
    fun initialize(){
        val account = accountsRepository.save(Account(accountId = null, money = 1000000))
        userRepository.save(User(
            userId = null,
            email = "admin@admin.com",
            username = "관리자",
            password = "admin",
            accountId = account.accountId!!,
            createdAt = LocalDateTime.now()
        ))
    }
    @Transactional
    fun create(createUserDto: CreateUserDto): User{
        val account = accountsRepository.save(Account(accountId = null, money = 1000000))
        return userRepository.save( User(
            userId = null,
            email = createUserDto.email,
            username = createUserDto.username,
            password = createUserDto.password,
            accountId = account.accountId!!,
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
        return userRepository.findById(userId).getOrNull()
    }

    fun findUser(email: String, password: String): User? {
        val userList = userRepository.findByEmailAndPassword(email, password)
        if (userList.isEmpty()){
            return null
        }
        // 계좌 내용도 같이 보여줘야 함

        return userList[0]
    }
}