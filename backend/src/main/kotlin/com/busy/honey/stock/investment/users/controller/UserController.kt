package com.busy.honey.stock.investment.users.controller

import com.busy.honey.stock.investment.accounts.service.AccountsService
import com.busy.honey.stock.investment.response.RestApiResponse
import com.busy.honey.stock.investment.stock.entity.StockPrice
import com.busy.honey.stock.investment.stock.entity.UserStock
import com.busy.honey.stock.investment.stock.service.StockService
import com.busy.honey.stock.investment.stock.service.UserStockService
import com.busy.honey.stock.investment.stocks.entity.Stocks
import com.busy.honey.stock.investment.stocks.service.StocksService
import com.busy.honey.stock.investment.users.service.UserService
import com.busy.honey.stock.investment.users.dto.CreateUserDto
import com.busy.honey.stock.investment.users.dto.UpdateUserDto
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = ["*"], allowedHeaders = ["*"])
class UserController (private val userService: UserService,
                      private val accountsService: AccountsService,
                      private val stockService: StockService,
                      private val stocksService: StocksService,
){

    @GetMapping("/{userId}")
    fun getUser(@PathVariable("userId") userId: Long): RestApiResponse{
        val user = this.userService.findUser(userId)
        val data = mutableMapOf<Any, Any>()
        if (user != null){
            val a = stockService.getUserOwnStocks(userId)
            val userAccount = accountsService.findById(user.accountId)!!
            val userOwnStockList = getStocksList(a)
            data["email"] = user.email
            data["userName"] = user.username
            data["totalEarningRate"] = stockService.calculateEarningRate(userId)
            data["accountPrice"] = userAccount.money
            data["stocksInfo"] = userOwnStockList
        }

        return RestApiResponse(
            status = "OK",
            description = "success",
            data = data
        )
    }

    private fun getStocksList(stocksList: List<UserStock>): Set<Stocks>{
        val result = mutableSetOf<Stocks>()
        for (item in stocksList){
            val stocks = stocksService.getStocks(item.stocksId)
            if(stocks != null){
                result.add(stocks)
            }
        }
        return result
    }

    @PostMapping
    fun createUser(@RequestBody createUserDto: CreateUserDto): RestApiResponse {
        val user = this.userService.create(createUserDto)
        val data = mutableMapOf<Any, Any>()
        data["createdAt"] = user.createdAt.toString()
        data["email"] = user.email
        data["userName"] = user.username
        data["userId"] = user.userId!!
        data["accountId"] = user.accountId

        return RestApiResponse(
            status = "OK",
            description = "success",
            data = data
        )
    }

    @PutMapping("/{userId}")
    fun updateUserInfo(@PathVariable("userId") userId: Long,
                       @RequestBody updateUserDto: UpdateUserDto): RestApiResponse{
        val data = mutableMapOf<Any, Any>()
        var status = "OK"
        var description = "success"
        try{
            val user = this.userService.update(userId, updateUserDto)
        }catch (e: Exception){
            status = "ERROR"
            description = e.localizedMessage
        }

        return RestApiResponse(
            status = status,
            description = description,
            data = data
        )
    }

    @DeleteMapping("/{userId}")
    fun deleteUser(@PathVariable("userId") userId: Long): RestApiResponse{
        this.userService.delete(userId)
        return RestApiResponse(
            status = "OK",
            description = "success",
            data = null
        )
    }

}