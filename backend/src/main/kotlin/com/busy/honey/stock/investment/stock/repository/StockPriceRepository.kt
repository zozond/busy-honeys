package com.busy.honey.stock.investment.stock.repository

import com.busy.honey.stock.investment.stock.entity.StockPrice
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface StockPriceRepository : JpaRepository<StockPrice, Long> {}
