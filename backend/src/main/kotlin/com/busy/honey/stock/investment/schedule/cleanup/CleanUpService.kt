package com.busy.honey.stock.investment.schedule.cleanup

import com.busy.honey.stock.investment.stock.repository.StockPriceRepository
import com.busy.honey.stock.investment.utils.Utils
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class CleanUpService (val stockPriceRepository: StockPriceRepository){

    @Scheduled(cron = "*/1 * * * * *")
    @Transactional
    fun cleanup() {
        // 1. 오늘 날짜가 아닌 체결되지 않은 호가 찾기
        val today = Utils.getTodayStartDateTime()
        val stockPriceList = stockPriceRepository.findByIsConcludedNotToday(isConcluded = false, today = today)

        // 2. 호가 전부 삭제
        for (stockPrice in stockPriceList){
            stockPriceRepository.deleteById(stockPrice.stockPriceId!!)
        }
    }
}