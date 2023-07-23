package com.busy.honey.stock.investment.stocks

import com.busy.honey.stock.investment.stocks.entity.Stocks
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface StocksRepository : JpaRepository<Stocks, Long> {
}