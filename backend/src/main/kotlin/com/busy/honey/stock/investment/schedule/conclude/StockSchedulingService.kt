package com.busy.honey.stock.investment.schedule.conclude

import com.busy.honey.stock.investment.accounts.service.AccountsService
import com.busy.honey.stock.investment.stock.entity.StockPrice
import com.busy.honey.stock.investment.stock.repository.JdslStockPriceRepositoryImpl
import com.busy.honey.stock.investment.stock.repository.StockPriceRepository
import com.busy.honey.stock.investment.users.service.UserService
import com.busy.honey.stock.investment.utils.Utils
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class StockSchedulingService (private val userService: UserService,
                              private val accountsService: AccountsService,
                              private val stockPriceRepository: StockPriceRepository,
                              private val jdslStockPriceRepository: JdslStockPriceRepositoryImpl
){

    @Scheduled(cron = "*/1 * * * * *")
    @Transactional
    fun schedule() {
        // 오늘 날짜 기준
        val startDate = Utils.getTodayStartDateTime()
        val endDate = Utils.getTodayEndDateTime()

        // 1. 미체결된 매수 데이터 조회
        val buyStockPriceList = jdslStockPriceRepository.getBuyStockPriceList(startDate, endDate)

        // 2. 미체결된 매도 데이터 조회
        val sellStockPriceList = jdslStockPriceRepository.getSellStockPriceList(startDate, endDate)

        // 3. 체결 가능한지 여부 확인
        // 매도 데이터를 순회하면서, 매수 데이터에 매치되는 데이터가 있는지 확인
        for (sellStockPrice in sellStockPriceList) {
            for (buyStockPrice in buyStockPriceList) {
                if (isTradeAcceptable(sellStockPrice, buyStockPrice))
                    trade(sellStockPrice, buyStockPrice)
            }
        }
    }

    fun isTradeAcceptable(sellStockPrice: StockPrice, buyStockPrice: StockPrice): Boolean {
        return sellStockPrice.stocksId == buyStockPrice.stocksId && sellStockPrice.price == buyStockPrice.price
    }

    fun trade(sellStockPrice: StockPrice, buyStockPrice: StockPrice){
        val seller = userService.findUser(sellStockPrice.userId)!!
        val buyer = userService.findUser(buyStockPrice.userId)!!
        if(sellStockPrice.amount <= buyStockPrice.amount){
            sellStockPrice.isConcluded = true
        }
        buyStockPrice.isConcluded = true
        sellStockPrice.amount -= buyStockPrice.amount
        stockPriceRepository.save(sellStockPrice)
        stockPriceRepository.save(buyStockPrice)
        accountsService.deposit(seller.accountId, buyer.accountId, sellStockPrice.price)
    }
}