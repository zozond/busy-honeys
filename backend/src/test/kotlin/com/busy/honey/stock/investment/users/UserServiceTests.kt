package com.busy.honey.stock.investment.users

import com.busy.honey.stock.investment.users.dto.CreateUserDto
import com.busy.honey.stock.investment.users.dto.UpdateUserDto
import com.busy.honey.stock.investment.users.entity.User
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import java.time.LocalDateTime

@ExtendWith(MockitoExtension::class)
class UserServiceTests {

    lateinit var userService: UserService

    @BeforeEach
    fun setup(){
        this.userService = UserService()
    }

    @Test
    @DisplayName("유저 가져오기 테스트")
    fun findUserTest(){
        val userId = 0L
        val user = User(
            userId = userId,
            email = "test@humi.com",
            userName = "helloworld",
            password = "helloworld",
            createdAt = LocalDateTime.now()
        )
        userService.repository[userId] = user
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
        val user = userService.create(createUserDto)
        val userId = user.userId
        assertEquals(user, userService.findUser(userId))
    }

    @Test
    @DisplayName("유저 삭제 테스트")
    fun deleteTest(){
        val userId = 0L
        val user = User(
            userId = userId,
            email = "test@humi.com",
            userName = "helloworld",
            password = "helloworld",
            createdAt = LocalDateTime.now()
        )
        userService.repository[userId] = user
        assertEquals(user, userService.findUser(userId))

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
        val user = userService.create(createUserDto)
        val userId = user.userId
        assertEquals(user, userService.findUser(userId))

        val newUsername = "helloworld2"
        val newPassword = "admin"
        val newPasswordConfirm = "admin"
        val updateUserDto = UpdateUserDto(newUsername, newPassword, newPasswordConfirm)
        assertNotEquals(user, userService.update(userId, updateUserDto))
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
        val user = userService.create(createUserDto)
        val userId = user.userId
        assertEquals(user, userService.findUser(userId))

        val newUsername = "helloworld2"
        val newPassword = "admin"
        val newPasswordConfirm = "admin2"
        val updateUserDto = UpdateUserDto(newUsername, newPassword, newPasswordConfirm)
        assertThrows(Exception::class.java) { userService.update(userId, updateUserDto) }
    }

    @Test
    @DisplayName("유저 정보 변경 테스트, 만약 유저가 생성되지 않았는데 유저를 변경한 경우")
    fun updateTest3(){
        val userId = 0L
        val newUsername = "helloworld2"
        val newPassword = "admin"
        val newPasswordConfirm = "admin2"
        val updateUserDto = UpdateUserDto(newUsername, newPassword, newPasswordConfirm)
        assertThrows(Exception::class.java) { userService.update(userId, updateUserDto) }
    }
}