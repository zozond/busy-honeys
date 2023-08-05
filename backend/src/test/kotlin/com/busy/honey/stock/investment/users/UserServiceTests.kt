package com.busy.honey.stock.investment.users

import com.busy.honey.stock.investment.accounts.AccountsRepository
import com.busy.honey.stock.investment.accounts.entity.Account
import com.busy.honey.stock.investment.stocks.StocksService
import com.busy.honey.stock.investment.users.dto.CreateUserDto
import com.busy.honey.stock.investment.users.dto.UpdateUserDto
import com.busy.honey.stock.investment.users.entity.User
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.*
import java.time.LocalDateTime
import java.util.*

@ExtendWith(MockitoExtension::class)
class UserServiceTests {

    lateinit var userService: UserService
    lateinit var userRepository: UserRepository
    lateinit var accountsRepository:  AccountsRepository

    @BeforeEach
    fun setup() {
        userRepository = mock()
        accountsRepository = mock()
        userService = UserService(
            userRepository = userRepository,
            accountsRepository = accountsRepository
        )
    }

    @Test
    @DisplayName("유저 가져오기 테스트")
    fun findUserTest(){
        val userId = 0L
        val user = User(
            userId = userId,
            email = "test@humi.com",
            username = "helloworld",
            password = "helloworld",
            accountId = 0,
            createdAt = LocalDateTime.now()
        )

        whenever(userRepository.findById(userId)).thenReturn(Optional.of(user))
        assertEquals(user, userService.findUser(userId))
    }

    @Test
    @DisplayName("유저 생성 테스트")
    fun createTest(){
        val email = "test@humi.com"
        val username = "helloworld"
        val password = "helloworld"
        val createUserDto = CreateUserDto(
            email = email,
            username = username,
            password = password
        )
        val userId = 0L
        val user = User(
            userId = userId,
            email = email,
            username = username,
            password = password,
            accountId = 0,
            createdAt = LocalDateTime.now()
        )
        val account = Account(accountId = 0L, money = 1000000)
        whenever(accountsRepository.save(anyVararg(Account::class))).thenReturn(account)
        whenever(userRepository.save(anyVararg(User::class))).thenReturn(user)
        assertEquals(user, userService.create(createUserDto))
    }

    @Test
    @DisplayName("유저 삭제 테스트")
    fun deleteTest(){
        val email = "test@humi.com"
        val username = "helloworld"
        val password = "helloworld"
        val createUserDto = CreateUserDto(
            email = email,
            username = username,
            password = password
        )
        val userId = 0L
        val user = User(
            userId = userId,
            email = email,
            username = username,
            password = password,
            accountId = 0,
            createdAt = LocalDateTime.now()
        )
        val account = Account(accountId = 0L, money = 1000000)
        whenever(accountsRepository.save(anyVararg(Account::class))).thenReturn(account)
        whenever(userRepository.save(anyVararg(User::class))).thenReturn(user)
        whenever(userRepository.findById(userId)).thenReturn(Optional.empty())
        assertEquals(user, userService.create(createUserDto))

        userService.delete(userId)
        assertNull(userService.findUser(userId))
    }

    @Test
    @DisplayName("유저 정보 변경 테스트")
    fun updateTest(){
        val email = "test@humi.com"
        val username = "helloworld"
        val password = "helloworld"
        val createUserDto = CreateUserDto(
            email = email,
            username = username,
            password = password
        )
        val userId = 0L
        val user = User(
            userId = userId,
            email = email,
            username = username,
            password = password,
            accountId = 0,
            createdAt = LocalDateTime.now()
        )
        val newUsername = "helloworld2"
        val newPassword = "admin"
        val newPasswordConfirm = "admin"
        val updateUserDto = UpdateUserDto(newUsername, newPassword, newPasswordConfirm)
        val updatedUser = User(
            userId = userId,
            email = email,
            username = newUsername,
            password = newPassword,
            accountId = 0,
            createdAt = user.createdAt
        )

        val userService : UserService = mock()
        whenever(userService.create(createUserDto)).thenReturn(user)
        whenever(userService.update(userId, updateUserDto)).thenReturn(updatedUser)

        val result =  userService.create(createUserDto)
        assertEquals(user, result)
        assertNotEquals(username, userService.update(userId, updateUserDto).username)
    }

    @Test
    @DisplayName("유저 정보 변경 테스트, 만약 패스워드와 컨펌된 패스워드가 같지 않을 때")
    fun updateTest2(){
        val email = "test@humi.com"
        val username = "helloworld"
        val password = "helloworld"
        val createUserDto = CreateUserDto(
            email = email,
            username = username,
            password = password
        )
        val userId = 0L
        val user = User(
            userId = userId,
            email = email,
            username = username,
            password = password,
            accountId = 0,
            createdAt = LocalDateTime.now()
        )
        val newUsername = "helloworld2"
        val newPassword = "admin"
        val newPasswordConfirm = "admin2"
        val updateUserDto = UpdateUserDto(newUsername, newPassword, newPasswordConfirm)
        val userService : UserService = mock()
        whenever(userService.create(createUserDto)).thenReturn(user)
        whenever(userService.update(userId, updateUserDto)).then {
            throw Exception("새로운 패스워드와 컨펌된 패스워드가 같지 않음.")
        }

        val result =  userService.create(createUserDto)
        assertEquals(user, result)
        assertThrows(Exception::class.java) {
            userService.update(userId, updateUserDto)
        }
    }

    @Test
    @DisplayName("유저 정보 변경 테스트, 만약 유저가 생성되지 않았는데 유저를 변경한 경우")
    fun updateTest3(){
        val userId = 0L
        val newUsername = "helloworld2"
        val newPassword = "admin"
        val newPasswordConfirm = "admin2"
        val updateUserDto = UpdateUserDto(newUsername, newPassword, newPasswordConfirm)
        val userService : UserService = mock()
        whenever(userService.update(userId, updateUserDto)).then {
            throw Exception("유저가 생성되지 않았는데 업데이트 한 경우")
        }

        assertThrows(Exception::class.java) {
            userService.update(userId, updateUserDto)
        }
    }
}