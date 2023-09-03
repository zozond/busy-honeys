package com.busy.honey.stock.investment.schedule.conclude

import com.busy.honey.stock.investment.accounts.service.AccountsService
import com.busy.honey.stock.investment.stock.entity.StockPrice
import com.busy.honey.stock.investment.stock.entity.UserStock
import com.busy.honey.stock.investment.stock.repository.JdslStockPriceRepository
import com.busy.honey.stock.investment.stock.repository.StockPriceRepository
import com.busy.honey.stock.investment.stock.service.UserStockService
import com.busy.honey.stock.investment.users.service.UserService
import com.busy.honey.stock.investment.utils.Utils
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime


@Service
class StockSchedulingService (private val userService: UserService,
                              private val userStockService: UserStockService,
                              private val accountsService: AccountsService,
                              private val stockPriceRepository: StockPriceRepository,
                              private val jdslStockPriceRepository: JdslStockPriceRepository
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
                if (isTradeAcceptable(sellStockPrice, buyStockPrice)){
                    trade(sellStockPrice, buyStockPrice)
                    tradeAfter(sellStockPrice, buyStockPrice)
                }

            }
        }
    }

    fun isTradeAcceptable(sellStockPrice: StockPrice, buyStockPrice: StockPrice): Boolean {
        if (sellStockPrice.stocksId == buyStockPrice.stocksId){
            if (sellStockPrice.price == buyStockPrice.price){
                return true
            }
            return false
        }else{
            return false
        }

    }

    @Transactional
    fun trade(sellStockPrice: StockPrice, buyStockPrice: StockPrice){
        val seller = userService.findUser(sellStockPrice.userId)!!
        val buyer = userService.findUser(buyStockPrice.userId)!!

        if(sellStockPrice.amount <= buyStockPrice.amount){
            sellStockPrice.isConcluded = true
        }

        buyStockPrice.isConcluded = true
        sellStockPrice.amount -= buyStockPrice.amount

        stockPriceRepository.save(sellStockPrice) // 매치됨
        stockPriceRepository.save(buyStockPrice)  // 매치됨

        // 출금
        accountsService.deposit(seller.accountId, buyer.accountId, sellStockPrice.price)
    }

    @Transactional
    fun tradeAfter(sellStockPrice: StockPrice, buyStockPrice: StockPrice){
        // 매도자 주식내역 제거
        userStockService.deleteStock(
            userId=sellStockPrice.userId,
            stocksId=sellStockPrice.stocksId,
            amount=buyStockPrice.amount
        )

        // 매수자 주식내역으로 저장
        userStockService.saveStock(UserStock(
            userStockId=null,
            userId=buyStockPrice.userId,
            price=buyStockPrice.price, // 산 가격
            stocksId=buyStockPrice.stocksId,
            amount=buyStockPrice.amount,
            timestamp=LocalDateTime.now()
        ))
    }
}