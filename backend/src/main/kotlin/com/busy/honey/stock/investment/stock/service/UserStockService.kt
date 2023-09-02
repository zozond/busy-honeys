package com.busy.honey.stock.investment.stock.service

import com.busy.honey.stock.investment.stock.entity.StockPrice
import com.busy.honey.stock.investment.stock.entity.UserStock
import com.busy.honey.stock.investment.stock.repository.UserStockRepository
import com.linecorp.kotlinjdsl.querydsl.expression.column
import com.linecorp.kotlinjdsl.spring.data.SpringDataQueryFactory
import com.linecorp.kotlinjdsl.spring.data.listQuery
import org.springframework.stereotype.Service

@Service
class UserStockService (
    private val userStockRepository: UserStockRepository,
    private val queryFactory: SpringDataQueryFactory
){
    fun getStockList(userId: Long): List<UserStock>{
        return queryFactory.listQuery<UserStock> {
            select(entity(UserStock::class))
            from(entity(UserStock::class))
            whereAnd(
                column(UserStock::userId).equal(userId)
            )
            orderBy(column(UserStock::price).asc())
        }
    }

    fun haveStock(userId: Long, stocksId: Long): Boolean{
        val result: List<UserStock> = queryFactory.listQuery{
            select(entity(UserStock::class))
            from(entity(UserStock::class))
            whereAnd(
                column(UserStock::userId).equal(userId),
                column(UserStock::stocksId).equal(stocksId)
            )
            orderBy(column(UserStock::price).asc())
        }

        return result.isNotEmpty()
    }

    fun saveStock(userStock: UserStock){
        userStockRepository.save(userStock)
    }

    fun deleteStock(userId: Long, stocksId: Long, amount: Int){
        val list = queryFactory.listQuery<UserStock> {
            select(entity(UserStock::class))
            from(entity(UserStock::class))
            whereAnd(
                column(UserStock::userId).equal(userId),
                column(UserStock::stocksId).equal(stocksId)
            )
            orderBy(column(UserStock::price).asc())
        }

        if(list.isEmpty()){
            return
//            throw Exception("해당 주식을 보유하고 있지 않습니다")
        }

        var copyAmount = amount
        for(i: Int in 0 until list.size){
            if (copyAmount >= list[i].amount){
                copyAmount -= list[i].amount
                userStockRepository.deleteById(list[i].userStockId!!)
            }else{
                val userStock = list[i]
                userStock.amount -= copyAmount
                userStockRepository.save(userStock)
                copyAmount = 0
                break
            }
        }
    }
}