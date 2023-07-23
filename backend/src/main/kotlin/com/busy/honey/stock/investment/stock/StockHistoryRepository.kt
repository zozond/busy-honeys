package com.busy.honey.stock.investment.stock

import com.busy.honey.stock.investment.stock.entity.StockHistory
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface StockHistoryRepository : JpaRepository<StockHistory, Long>{

    fun findByUserId(userId:Long,  pageable: Pageable): List<StockHistory>
}