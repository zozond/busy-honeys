package com.busy.honey.stock.investment.stock.repository

import com.busy.honey.stock.investment.stock.entity.StockPrice
import com.linecorp.kotlinjdsl.querydsl.expression.column
import com.linecorp.kotlinjdsl.spring.data.SpringDataQueryFactory
import com.linecorp.kotlinjdsl.spring.data.listQuery
import org.springframework.stereotype.Repository
import java.time.LocalDateTime


interface JdslStockPriceRepository {
    fun getBuyStockPriceList(startDate: LocalDateTime, endDate: LocalDateTime): List<StockPrice>

    fun getSellStockPriceList(startDate: LocalDateTime, endDate: LocalDateTime): List<StockPrice>

    fun countConcludedTrade(stocksId: Long, startDate: LocalDateTime, endDate: LocalDateTime): Long

    fun findStockPriceOrderByTimestampDesc(
        stocksId: Long,
        isConcluded: Boolean,
        from: LocalDateTime,
        to: LocalDateTime,
        offset: Int,
        limit: Int
    ): List<StockPrice>

    fun findStockPriceOrderByTimestampAsc(
        stocksId: Long,
        isConcluded: Boolean,
        from: LocalDateTime,
        to: LocalDateTime,
        offset: Int,
        limit: Int
    ): List<StockPrice>

    fun findStockPriceOrderByPriceDesc(
        stocksId: Long,
        isConcluded: Boolean,
        from: LocalDateTime,
        to: LocalDateTime,
        offset: Int,
        limit: Int
    ): List<StockPrice>

    fun findStockPriceOrderByPriceAsc(
        stocksId: Long,
        isConcluded: Boolean,
        from: LocalDateTime,
        to: LocalDateTime,
        offset: Int,
        limit: Int
    ): List<StockPrice>

    fun findStockPriceForQuote(
        stocksId: Long,
        from: LocalDateTime,
        to: LocalDateTime,
        offset: Int,
        limit: Int
    ): List<StockPrice>

    fun findStockPriceForAllQuote(from: LocalDateTime, to: LocalDateTime, offset: Int, limit: Int): List<StockPrice>

    fun findByUserOwnAllStockPrice(userId: Long): List<StockPrice>

    fun findByIsConcludedNotToday(isConcluded: Boolean, today: LocalDateTime): List<StockPrice>

    fun findByLastPrice(
        isConcluded: Boolean,
        stocksId: Long,
        from: LocalDateTime,
        to: LocalDateTime,
        type: String,
        offset: Int,
        limit: Int
    ): List<StockPrice>

    fun findByIsConcludedBot(isConcluded: Boolean, today: LocalDateTime): List<StockPrice>
}
