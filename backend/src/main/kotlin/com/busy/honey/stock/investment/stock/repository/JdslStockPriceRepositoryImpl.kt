package com.busy.honey.stock.investment.stock.repository

import com.busy.honey.stock.investment.stock.entity.Count
import com.busy.honey.stock.investment.stock.entity.StockPrice
import com.linecorp.kotlinjdsl.querydsl.expression.column
import com.linecorp.kotlinjdsl.spring.data.SpringDataQueryFactory
import com.linecorp.kotlinjdsl.spring.data.listQuery
import org.springframework.stereotype.Repository
import java.time.LocalDateTime


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
                column(StockPrice::timestamp).lessThan(today),
                column(StockPrice::type).equal("normal")
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


    override fun findByIsConcludedBot(isConcluded: Boolean, today: LocalDateTime): List<StockPrice> {
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
}
