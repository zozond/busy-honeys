package com.busy.honey.stock.investment

import com.busy.honey.stock.investment.accounts.entity.Account
import com.busy.honey.stock.investment.accounts.repository.AccountsRepository
import com.busy.honey.stock.investment.users.dto.CreateUserDto
import com.busy.honey.stock.investment.users.entity.User
import com.busy.honey.stock.investment.users.repository.JdslUserRepository
import com.busy.honey.stock.investment.users.repository.UserRepository
import com.busy.honey.stock.investment.users.service.UserService
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.arbitrary
import io.kotest.property.arbitrary.string
import io.kotest.property.arbitrary.stringPattern
import io.kotest.property.checkAll
import io.kotest.property.exhaustive.exhaustive
import io.mockk.every
import io.mockk.mockk

import java.time.LocalDateTime



class UsersTest : BehaviorSpec({
    var userRepository = mockk<UserRepository>()
    var jdslUserRepository = mockk<JdslUserRepository>()
    var accountsRepository = mockk<AccountsRepository>()

    var userService = UserService(userRepository, jdslUserRepository, accountsRepository)

    Given("유저 생성 DTO 속성 기반 테스트") {
        val createUserDtoArb = arbitrary {
            val username = Arb.string(5..12).bind()
            val email = Arb.stringPattern("^[a-zA-Z0-9]+@[a-zA-Z0-9]+.[a-zA-Z]+\$").bind()
            val password = Arb.string(8).bind()

            CreateUserDto(email, username, password)
        }

        beforeEach {
            userRepository = mockk<UserRepository>()
            jdslUserRepository = mockk<JdslUserRepository>()
            accountsRepository = mockk<AccountsRepository>()
            userService = UserService(userRepository, jdslUserRepository, accountsRepository)
        }

        When("유저 생성 DTO로 유저 생성"){
            val userId = 1L
            val accountId = 1L

            createUserDtoArb.checkAll {it ->
                every { userRepository.save(any()) } returns User(userId=userId, email=it.email, username=it.username, password=it.password, accountId=accountId, userType="normal", createdAt = LocalDateTime.now())
                every { accountsRepository.save(any()) } answers { Account(accountId = accountId, money = 10000) }

                userService.create(it).userId shouldBe 1
            }
        }
    }

    Given("유저 생성 DTO 전송") {

        val email = "admin@example.com"
        val username = "aasdfadsf"
        val password = "qqqqqq"
        val createUserDto = CreateUserDto(email=email, username=username, password=password)

        beforeEach {
            userRepository = mockk<UserRepository>()
            jdslUserRepository = mockk<JdslUserRepository>()
            accountsRepository = mockk<AccountsRepository>()
            userService = UserService(userRepository, jdslUserRepository, accountsRepository)
        }

        When("유저 생성 DTO로 유저 생성"){
            val userId = 1L
            val accountId = 1L

            every { accountsRepository.save(any()) } answers { Account(accountId = accountId, money = 10000) }
            every { userRepository.save(any()) } answers { User(userId=userId, email=email, username=username, password=password, accountId=accountId, userType="normal", createdAt = LocalDateTime.now()) }

            val result = userService.create(createUserDto)

            Then("유저 생성 성공"){
                1L shouldBe result.userId
            }
        }
    }
})
