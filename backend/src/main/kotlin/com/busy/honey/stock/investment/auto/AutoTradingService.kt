package com.busy.honey.stock.investment.auto

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AutoTradingService {

    @Scheduled(cron = "*/1 * * * * *")
    @Transactional
    fun autotrade() {
        // 1. bot 유저 찾기
        // 2. bot 유저가 관심
    }
}