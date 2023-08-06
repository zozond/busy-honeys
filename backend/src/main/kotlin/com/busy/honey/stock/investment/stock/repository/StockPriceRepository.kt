package com.busy.honey.stock.investment.stock.repository

import com.busy.honey.stock.investment.stock.entity.StockPrice
import com.linecorp.kotlinjdsl.querydsl.expression.column
import com.linecorp.kotlinjdsl.spring.data.SpringDataQueryFactory
import com.linecorp.kotlinjdsl.spring.data.listQuery
import org.springframework.data.jpa.repository.JpaRepository
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
}


interface StockPriceRepository : JpaRepository<StockPrice, Long>, JdslStockPriceRepository {}

data class Count(
    val count: Long
)

@Repository
class JdslStockPriceRepositoryImpl(
    private val queryFactory: SpringDataQueryFactory
) : JdslStockPriceRepository {
    override fun getBuyStockPriceList(startDate: LocalDateTime, endDate: LocalDateTime): List<StockPrice> {
        val result: List<StockPrice> = queryFactory.listQuery {
            select(entity(StockPrice::class))
            from(entity(StockPrice::class))
            whereAnd(
                column(StockPrice::isConcluded).isFalse(),
                column(StockPrice::type).equal("buy"),
                column(StockPrice::timestamp).between(startDate, endDate)
            )
        }
        return result
    }

    override fun getSellStockPriceList(startDate: LocalDateTime, endDate: LocalDateTime): List<StockPrice> {
        val result: List<StockPrice> = queryFactory.listQuery {
            select(entity(StockPrice::class))
            from(entity(StockPrice::class))
            whereAnd(
                column(StockPrice::isConcluded).isFalse(),
                column(StockPrice::type).equal("sell"),
                column(StockPrice::timestamp).between(startDate, endDate)
            )
        }
        return result
    }

    override fun countConcludedTrade(stocksId: Long, startDate: LocalDateTime, endDate: LocalDateTime): Long {
        val result: List<Count> = queryFactory.listQuery {
            selectMulti(count(column(StockPrice::stockPriceId)))
            from(entity(StockPrice::class))
            whereAnd(
                column(StockPrice::stocksId).equal(stocksId),
                column(StockPrice::isConcluded).isTrue(),
                column(StockPrice::timestamp).between(startDate, endDate)
            )
        }
        return result.get(0).count
    }

    override fun findStockPriceOrderByTimestampDesc(
        stocksId: Long,
        isConcluded: Boolean,
        from: LocalDateTime,
        to: LocalDateTime,
        offset: Int,
        limit: Int
    ): List<StockPrice> {
        val result: List<StockPrice> = queryFactory.listQuery {
            select(entity(StockPrice::class))
            from(entity(StockPrice::class))
            whereAnd(
                column(StockPrice::stocksId).equal(stocksId),
                column(StockPrice::isConcluded).equal(isConcluded),
                column(StockPrice::timestamp).between(from, to)
            )
            orderBy(column(StockPrice::timestamp).desc())
            offset(offset)
            limit(limit)
        }
        return result
    }

    override fun findStockPriceOrderByTimestampAsc(
        stocksId: Long,
        isConcluded: Boolean,
        from: LocalDateTime,
        to: LocalDateTime,
        offset: Int,
        limit: Int
    ): List<StockPrice> {
        val result: List<StockPrice> = queryFactory.listQuery {
            select(entity(StockPrice::class))
            from(entity(StockPrice::class))
            whereAnd(
                column(StockPrice::stocksId).equal(stocksId),
                column(StockPrice::isConcluded).equal(isConcluded),
                column(StockPrice::timestamp).between(from, to)
            )
            offset(offset)
            limit(limit)
            orderBy(column(StockPrice::timestamp).asc())
        }
        return result
    }

    override fun findStockPriceOrderByPriceDesc(
        stocksId: Long,
        isConcluded: Boolean,
        from: LocalDateTime,
        to: LocalDateTime,
        offset: Int,
        limit: Int
    ): List<StockPrice> {
        val result: List<StockPrice> = queryFactory.listQuery {
            select(entity(StockPrice::class))
            from(entity(StockPrice::class))
            whereAnd(
                column(StockPrice::stocksId).equal(stocksId),
                column(StockPrice::isConcluded).equal(isConcluded),
                column(StockPrice::timestamp).between(from, to)
            )
            offset(offset)
            limit(limit)
            orderBy(column(StockPrice::price).desc())
        }
        return result
    }

    override fun findStockPriceOrderByPriceAsc(
        stocksId: Long,
        isConcluded: Boolean,
        from: LocalDateTime,
        to: LocalDateTime,
        offset: Int,
        limit: Int
    ): List<StockPrice> {
        val result: List<StockPrice> = queryFactory.listQuery {
            select(entity(StockPrice::class))
            from(entity(StockPrice::class))
            whereAnd(
                column(StockPrice::stocksId).equal(stocksId),
                column(StockPrice::isConcluded).equal(isConcluded),
                column(StockPrice::timestamp).between(from, to)
            )
            offset(offset)
            limit(limit)
            orderBy(column(StockPrice::price).asc())
        }
        return result
    }

    override fun findStockPriceForQuote(
        stocksId: Long,
        from: LocalDateTime,
        to: LocalDateTime,
        offset: Int,
        limit: Int
    ): List<StockPrice> {
        val result: List<StockPrice> = queryFactory.listQuery {
            select(entity(StockPrice::class))
            from(entity(StockPrice::class))
            whereAnd(
                column(StockPrice::stocksId).equal(stocksId),
                column(StockPrice::isConcluded).equal(false),
                column(StockPrice::timestamp).between(from, to)
            )
            offset(offset)
            limit(limit)
            orderBy(column(StockPrice::price).asc())
        }
        return result
    }


    override fun findStockPriceForAllQuote(
        from: LocalDateTime,
        to: LocalDateTime,
        offset: Int,
        limit: Int
    ): List<StockPrice> {
        val result: List<StockPrice> = queryFactory.listQuery {
            select(entity(StockPrice::class))
            from(entity(StockPrice::class))
            whereAnd(
                column(StockPrice::timestamp).between(from, to)
            )
            offset(offset)
            limit(limit)
            orderBy(column(StockPrice::price).asc())
        }
        return result
    }


    override fun findByUserOwnAllStockPrice(userId: Long): List<StockPrice> {
        val result: List<StockPrice> = queryFactory.listQuery {
            select(entity(StockPrice::class))
            from(entity(StockPrice::class))
            whereAnd(
                column(StockPrice::userId).equal(userId)
            )
        }
        return result
    }

    override fun findByIsConcludedNotToday(isConcluded: Boolean, today: LocalDateTime): List<StockPrice> {
        return queryFactory.listQuery {
            select(entity(StockPrice::class))
            from(entity(StockPrice::class))
            whereAnd(
                column(StockPrice::isConcluded).equal(isConcluded),
                column(StockPrice::timestamp).lessThan(today)
            )
        }
    }

    override fun findByLastPrice(
        isConcluded: Boolean,
        stocksId: Long,
        from: LocalDateTime,
        to: LocalDateTime,
        type: String,
        offset: Int,
        limit: Int
    ): List<StockPrice> {
        return queryFactory.listQuery {
            select(entity(StockPrice::class))
            from(entity(StockPrice::class))
            whereAnd(
                column(StockPrice::stocksId).equal(stocksId),
                column(StockPrice::isConcluded).equal(isConcluded),
                column(StockPrice::timestamp).between(from, to),
                column(StockPrice::type).equal(type),
            )
            offset(offset)
            limit(limit)
        }
    }
}
