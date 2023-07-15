package com.busy.honey.stock.investment.stocks

import com.busy.honey.stock.investment.stocks.dto.CreateStocksDto
import com.busy.honey.stock.investment.stocks.dto.UpdateStocksDto
import com.busy.honey.stock.investment.stocks.entity.Stocks
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class StocksService {
    val repository = mutableMapOf<Long, Stocks>()

    fun create(createStocksDto: CreateStocksDto): Stocks {
        val stocksId = repository.size.toLong()
        val stocks = Stocks(
            stocksId = stocksId,
            stocksName = createStocksDto.stocksName,
            financialStatementsContent = createStocksDto.financialStatementsContent,
            createdAt = LocalDateTime.now()
        )
        repository[stocksId] = stocks
        return stocks
    }

    fun update(stocksId: Long, updateStocksDto: UpdateStocksDto): Stocks {
        if (repository[stocksId] == null){
            throw Exception("생성되지 않은 종목을 업데이트")
        }

        val newStocks = Stocks(
            stocksId = stocksId,
            stocksName = updateStocksDto.stocksName,
            financialStatementsContent = updateStocksDto.financialStatementsContent,
            createdAt = LocalDateTime.now()
        )
        repository[stocksId] = newStocks
        return newStocks
    }

    fun delete(stocksId: Long): Stocks?{
        val stocks = repository[stocksId]
        repository.remove(stocksId)
        return stocks
    }

    fun getStocks(stocksId: Long): Stocks? {
        return repository[stocksId]
    }
}