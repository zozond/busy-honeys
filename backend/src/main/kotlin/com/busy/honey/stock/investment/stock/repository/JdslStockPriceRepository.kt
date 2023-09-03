package com.busy.honey.stock.investment.stock.repository

import com.busy.honey.stock.investment.stock.entity.Count
import com.busy.honey.stock.investment.stock.entity.StockPrice
import com.linecorp.kotlinjdsl.querydsl.expression.column
import com.linecorp.kotlinjdsl.spring.data.SpringDataQueryFactory
import com.linecorp.kotlinjdsl.spring.data.listQuery
import org.springframework.stereotype.Service
import java.time.LocalDateTime


@Service
class JdslStockPriceRepository(
    val queryFactory: SpringDataQueryFactory
) {
    fun getBuyStockPriceList(startDate: LocalDateTime, endDate: LocalDateTime): List<StockPrice> {
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

    fun getSellStockPriceList(startDate: LocalDateTime, endDate: LocalDateTime): List<StockPrice> {
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

    fun countConcludedTrade(stocksId: Long, startDate: LocalDateTime, endDate: LocalDateTime): Long {
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

    fun findStockPriceOrderByTimestampDesc(
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

    fun findStockPriceOrderByTimestampAsc(
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

    fun findStockPriceOrderByPriceDesc(
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

    fun findStockPriceOrderByPriceAsc(
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

    fun findStockPriceForQuote(
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


    fun findStockPriceForAllQuote(
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


    fun findByUserOwnAllStockPrice(userId: Long): List<StockPrice> {
        val result: List<StockPrice> = queryFactory.listQuery {
            select(entity(StockPrice::class))
            from(entity(StockPrice::class))
            whereAnd(
                column(StockPrice::userId).equal(userId),
                column(StockPrice::isConcluded).equal(true),
                column(StockPrice::type).equal("buy"),
            )
        }
        return result
    }

    fun findByUserOwnAllStockPrice(userId: Long, isConcluded: Boolean, type: String): List<StockPrice> {
        val result: List<StockPrice> = queryFactory.listQuery {
            select(entity(StockPrice::class))
            from(entity(StockPrice::class))
            whereAnd(
                column(StockPrice::userId).equal(userId),
                column(StockPrice::isConcluded).equal(isConcluded),
                column(StockPrice::type).equal(type)
            )
        }
        return result
    }

    fun findByIsConcludedNotToday(isConcluded: Boolean, today: LocalDateTime): List<StockPrice> {
        return queryFactory.listQuery {
            select(entity(StockPrice::class))
            from(entity(StockPrice::class))
            whereAnd(
                column(StockPrice::isConcluded).equal(isConcluded),
                column(StockPrice::timestamp).lessThan(today),
                column(StockPrice::type).equal("normal")
            )
        }
    }

    fun findByLastPrice(
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


    fun findByIsConcludedBot(isConcluded: Boolean, today: LocalDateTime): List<StockPrice> {
        return queryFactory.listQuery {
            select(entity(StockPrice::class))
            from(entity(StockPrice::class))
            whereAnd(
                column(StockPrice::isConcluded).equal(isConcluded),
                column(StockPrice::timestamp).lessThanOrEqualTo(today),
                column(StockPrice::type).equal("bot")
            )
        }
    }

    fun findByRecentConcluded(stocksId: Long, isConcluded: Boolean, limit: Int): List<StockPrice> {
        return queryFactory.listQuery {
            select(entity(StockPrice::class))
            from(entity(StockPrice::class))
            whereAnd(
                column(StockPrice::stocksId).equal(stocksId),
                column(StockPrice::isConcluded).equal(isConcluded),
            )
            limit(limit)
            orderBy(column(StockPrice::timestamp).desc())
        }
    }


    fun findByLastPrice(
        isConcluded: Boolean,
        stocksId: Long,
        type: String,
        limit: Int
    ): List<StockPrice> {
        return queryFactory.listQuery {
            select(entity(StockPrice::class))
            from(entity(StockPrice::class))
            whereAnd(
                column(StockPrice::stocksId).equal(stocksId),
                column(StockPrice::isConcluded).equal(isConcluded),
                column(StockPrice::type).equal(type),
            )
            limit(limit)
        }
    }
}
