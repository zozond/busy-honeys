package com.busy.honey.stock.investment.accounts

import com.busy.honey.stock.investment.accounts.dto.DepositDto
import com.busy.honey.stock.investment.response.RestApiResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/account")
class AccountsController (val accountsService: AccountsService){

    @PostMapping
    fun deposit(@RequestBody depositDto: DepositDto): RestApiResponse {
        accountsService.deposit(depositDto)
        return RestApiResponse(
            status = "OK",
            description = "",
            data = null
        )
    }
}