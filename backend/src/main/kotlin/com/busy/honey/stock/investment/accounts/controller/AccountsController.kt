package com.busy.honey.stock.investment.accounts.controller

import com.busy.honey.stock.investment.accounts.dto.DepositDto
import com.busy.honey.stock.investment.accounts.service.AccountsService
import com.busy.honey.stock.investment.response.RestApiResponse
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/account")
@CrossOrigin(origins = ["*"], allowedHeaders = ["*"])
class AccountsController (private val accountsService: AccountsService){

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