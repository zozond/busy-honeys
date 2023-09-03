package com.busy.honey.stock.investment.schedule.cleanup

import com.busy.honey.stock.investment.stock.repository.JdslStockPriceRepository
import com.busy.honey.stock.investment.stock.repository.StockPriceRepository
import com.busy.honey.stock.investment.utils.Utils
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class CleanUpService (private val stockPriceRepository: StockPriceRepository,
                      private val jdslStockPriceRepository: JdslStockPriceRepository
){

    @Scheduled(cron = "* * */1 * * *")
    @Transactional
    fun cleanup() {
        // 1. 오늘 날짜가 아닌 체결되지 않은 호가 찾기
        val today = Utils.getTodayStartDateTime()
        val stockPriceList = jdslStockPriceRepository.findByIsConcludedNotToday(isConcluded = false, today = today)

        // 2. 호가 전부 삭제
        for (stockPrice in stockPriceList){
            stockPriceRepository.deleteById(stockPrice.stockPriceId!!)
        }
    }

    @Scheduled(cron = "* */1 * * * *")
    @Transactional
    fun botCleanUp() {
        // 1. 봇이 만든 호가 찾기
        val today = Utils.getTodayStartDateTime()
        val stockPriceList = jdslStockPriceRepository.findByIsConcludedBot(isConcluded = false, today = today)

        // 2. 호가 전부 삭제
        for (stockPrice in stockPriceList){
            stockPriceRepository.deleteById(stockPrice.stockPriceId!!)
        }
    }
}