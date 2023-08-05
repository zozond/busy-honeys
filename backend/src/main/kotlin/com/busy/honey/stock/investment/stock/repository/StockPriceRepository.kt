package com.busy.honey.stock.investment.stock.repository

import com.busy.honey.stock.investment.stock.entity.StockPrice
import com.linecorp.kotlinjdsl.querydsl.expression.column
import com.linecorp.kotlinjdsl.spring.data.SpringDataQueryFactory
import com.linecorp.kotlinjdsl.spring.data.listQuery
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

//@Repository
//interface StockPriceRepository : JpaRepository<StockPrice, Long> {
//
//    // 체결된 종가/시가, 최고가/최저가
//    fun findByIsConcludedAndTimestampBetween( isConcluded: Boolean, from: LocalDateTime, to: LocalDateTime, pageable: Pageable): List<StockPrice>
//
//    // 체결된 거래량
//    fun countByTimestampAndIsConcluded(timestamp: LocalDateTime, isConcluded: Boolean): Long
//
//}


interface JdslStockPriceRepository {
    fun getBuyStockPriceList(startDate: LocalDateTime, endDate: LocalDateTime): List<StockPrice>
    fun getSellStockPriceList(startDate: LocalDateTime, endDate: LocalDateTime): List<StockPrice>
    fun countConcludedTrade(startDate: LocalDateTime, endDate: LocalDateTime): Long
    fun findStockPriceOrderByTimestampDesc(isConcluded: Boolean, from: LocalDateTime, to: LocalDateTime, offset: Int, limit: Int): List<StockPrice>
    fun findStockPriceOrderByTimestampAsc(isConcluded: Boolean, from: LocalDateTime, to: LocalDateTime, offset: Int, limit: Int): List<StockPrice>
    fun findStockPriceOrderByPriceDesc(isConcluded: Boolean, from: LocalDateTime, to: LocalDateTime, offset: Int, limit: Int): List<StockPrice>
    fun findStockPriceOrderByPriceAsc(isConcluded: Boolean, from: LocalDateTime, to: LocalDateTime, offset: Int, limit: Int): List<StockPrice>
    fun findStockPriceForQuote(from: LocalDateTime, to: LocalDateTime, offset: Int, limit: Int): List<StockPrice>
}


interface StockPriceRepository : JpaRepository<StockPrice, Long>, JdslStockPriceRepository {

}

data class Count(
    val count: Long
)


@Repository
class JdslStockPriceRepositoryImpl (
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

    override fun countConcludedTrade(startDate: LocalDateTime, endDate: LocalDateTime): Long{
        val result: List<Count> = queryFactory.listQuery {
            selectMulti(count(column(StockPrice::stockPriceId)))
            from(entity(StockPrice::class))
            whereAnd(
                column(StockPrice::isConcluded).isTrue(),
                column(StockPrice::timestamp).between(startDate, endDate)
            )
        }
        return result.get(0).count
    }

    override fun findStockPriceOrderByTimestampDesc(
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
                column(StockPrice::isConcluded).equal(isConcluded),
                column(StockPrice::timestamp).between(from, to)
            )
            offset(offset)
            limit(limit)
            orderBy(column(StockPrice::timestamp).desc())
        }
        return result
    }

    override fun findStockPriceOrderByTimestampAsc(
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
        from: LocalDateTime,
        to: LocalDateTime,
        offset: Int,
        limit: Int
    ): List<StockPrice> {
        val result: List<StockPrice> = queryFactory.listQuery {
            select(entity(StockPrice::class))
            from(entity(StockPrice::class))
            whereAnd(
                column(StockPrice::isConcluded).equal(false),
                column(StockPrice::type).equal("sell"),
                column(StockPrice::timestamp).between(from, to)
            )
            offset(offset)
            limit(limit)
            orderBy(column(StockPrice::price).asc())
        }
        return result
    }
}
