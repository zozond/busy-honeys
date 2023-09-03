package com.busy.honey.stock.investment.init

import com.busy.honey.stock.investment.stock.service.StockService
import com.busy.honey.stock.investment.stocks.service.StocksService
import com.busy.honey.stock.investment.stocks.dto.CreateStocksDto
import com.busy.honey.stock.investment.users.service.UserService
import com.busy.honey.stock.investment.utils.Utils
import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Service
import kotlin.random.Random

@Service
class PostConstructService(private val userService: UserService,
                           private val stockService: StockService,
                           private val stocksService: StocksService
) {

    @PostConstruct
    fun initialize() {
        // 관리자
        userService.createAdminAndBot(email = "admin@admin.com", username = "admin", password = "admin", userType = "admin")

        // 봇
        userService.createAdminAndBot(email = "bot1@admin.com", username = "bot1", password = "bot1", userType = "bot")
        userService.createAdminAndBot(email = "bot2@admin.com", username = "bot2", password = "bot2", userType = "bot")
        userService.createAdminAndBot(email = "bot3@admin.com", username = "bot3", password = "bot3", userType = "bot")
        userService.createAdminAndBot(email = "bot4@admin.com", username = "bot4", password = "bot4", userType = "bot")
        userService.createAdminAndBot(email = "bot5@admin.com", username = "bot5", password = "bot5", userType = "bot")
        userService.createAdminAndBot(email = "bot6@admin.com", username = "bot6", password = "bot6", userType = "bot")
        userService.createAdminAndBot(email = "bot7@admin.com", username = "bot7", password = "bot7", userType = "bot")
        userService.createAdminAndBot(email = "bot8@admin.com", username = "bot8", password = "bot8", userType = "bot")
        userService.createAdminAndBot(email = "bot9@admin.com", username = "bot9", password = "bot9", userType = "bot")

        // 종목 생성
        val stocks1 = this.stocksService.create(CreateStocksDto(stocksName="바쁜벌꿀1", financialStatementsContent="매우 좋은 지표1"))
//        val stocks2 =this.stocksService.create(CreateStocksDto(stocksName="바쁜벌꿀2", financialStatementsContent="매우 좋은 지표2"))
//        val stocks3 =this.stocksService.create(CreateStocksDto(stocksName="바쁜벌꿀3", financialStatementsContent="매우 좋은 지표3"))

        // 일별 데이터 자동 생성
        val list = Utils.getOneMonthDateRange()
        val random = Random(10000)
        for (yyyymmdd in list){
            val startDate = Utils.getStartDateTime(yyyymmdd)

            var price = random.nextInt(1000, 1000000)
            var amount = random.nextInt(1000,1000000)
            this.stockService.insertSampleData(stocks1.stocksId!!, startDate, price, amount)

            price = random.nextInt(1000, 1000000)
            amount = random.nextInt(1000, 1000000)
            this.stockService.insertSampleData(stocks1.stocksId!!, startDate, price, amount)

            price = random.nextInt(1000, 1000000)
            amount = random.nextInt(1000, 1000000)
            this.stockService.insertSampleData(stocks1.stocksId!!, startDate, price, amount)

            price = random.nextInt(1000, 1000000)
            amount = random.nextInt(1000, 1000000)
            this.stockService.insertSampleData(stocks1.stocksId!!, startDate, price, amount)

            price = random.nextInt(1000, 1000000)
            amount = random.nextInt(1000, 1000000)
            this.stockService.insertSampleData(stocks1.stocksId!!, startDate, price, amount)



//            price = random.nextInt(1000, 1000000)
//            amount = random.nextInt(1000, 1000000)
//            this.stockService.insertSampleData(stocks2.stocksId!!, startDate, price, amount)
//
//            price = random.nextInt(1000, 1000000)
//            amount = random.nextInt(1000, 1000000)
//            this.stockService.insertSampleData(stocks2.stocksId!!, startDate, price, amount)
//
//            price = random.nextInt(1000, 1000000)
//            amount = random.nextInt(1000, 1000000)
//            this.stockService.insertSampleData(stocks2.stocksId!!, startDate, price, amount)
//
//            price = random.nextInt(1000, 1000000)
//            amount = random.nextInt(1000, 1000000)
//            this.stockService.insertSampleData(stocks2.stocksId!!, startDate, price, amount)
//
//            price = random.nextInt(1000, 1000000)
//            amount = random.nextInt(1000, 1000000)
//            this.stockService.insertSampleData(stocks2.stocksId!!, startDate, price, amount)
//
//
//            price = random.nextInt(1000, 1000000)
//            amount = random.nextInt(1000, 1000000)
//            this.stockService.insertSampleData(stocks3.stocksId!!, startDate, price, amount)
//
//            price = random.nextInt(1000, 1000000)
//            amount = random.nextInt(1000, 1000000)
//            this.stockService.insertSampleData(stocks3.stocksId!!, startDate, price, amount)
//
//            price = random.nextInt(1000, 1000000)
//            amount = random.nextInt(1000, 1000000)
//            this.stockService.insertSampleData(stocks3.stocksId!!, startDate, price, amount)
//
//            price = random.nextInt(1000, 1000000)
//            amount = random.nextInt(1000, 1000000)
//            this.stockService.insertSampleData(stocks3.stocksId!!, startDate, price, amount)
//
//            price = random.nextInt(1000, 1000000)
//            amount = random.nextInt(1000, 1000000)
//            this.stockService.insertSampleData(stocks3.stocksId!!, startDate, price, amount)
        }
    }

}