package com.busy.honey.stock.investment.schedule.autotrade

import com.busy.honey.stock.investment.stock.service.StockService
import com.busy.honey.stock.investment.stock.dto.BuyStockDto
import com.busy.honey.stock.investment.stock.dto.SellStockDto
import com.busy.honey.stock.investment.stocks.service.StocksService
import com.busy.honey.stock.investment.users.service.UserService
import com.busy.honey.stock.investment.utils.Utils
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.security.SecureRandom

@Service
class AutoTradingService (private val userService: UserService,
                          private val stocksService: StocksService,
                          private val stockService: StockService
){

    val randomStocks = SecureRandom()
    val randomActions = SecureRandom()
    val randomPriceValue = SecureRandom()

    @Scheduled(cron = "*/1 * * * * *")
    @Transactional
    fun autotrade() {
        // 1. Bot 유저 찾기
        // - Bot은 돈이 무제한임
        val botList = userService.findBotList()

        // 2. 종목 찾기
        val stocksList = stocksService.getAllStocks()
        if (stocksList.isEmpty()){
            return
        }

        val from = Utils.getTodayStartDateTime()
        val to = Utils.getTodayEndDateTime()
        for (botUser in botList){
            // 3. bot 유저가 랜덤으로 종목을 정해 매도/매수 진행
            val randomStocksIndex = randomStocks.nextInt(stocksList.size)
            val value = randomActions.nextInt(10000)
            val sumPrice = randomPriceValue.nextInt(50)
            val stocks = stocksList.get(randomStocksIndex)

            // 3. 마지막 매수, 매도 값 확인
            val lastBuyPrice = stockService.lastBuyPrice(stocksId = stocks.stocksId!!, from=from, to=to)
            val lastSellPrice = stockService.lastSellPrice(stocksId = stocks.stocksId!!, from=from, to=to)

            if (value < 5000){
                // 매도
                stockService.sellStock(SellStockDto(
                    userId = botUser.userId!!,
                    stocksId = stocks.stocksId!!,
                    stockAmount = 1,
                    askPrice = lastBuyPrice + sumPrice
                ))
            }else {
                // 매수
                stockService.buyStock(
                    BuyStockDto(
                        userId = botUser.userId!!,
                        stocksId = stocks.stocksId!!,
                        stockAmount = 1,
                        bidPrice = lastSellPrice + sumPrice
                    )
                )
            }
            // 3. 만약 이전에 매수/매도 진행했던 내용이 있다면 그건 그대로 무시
            // why? -> 클린업 프로세스에서 하루가 지나면 알아서 삭제
        }
    }
}