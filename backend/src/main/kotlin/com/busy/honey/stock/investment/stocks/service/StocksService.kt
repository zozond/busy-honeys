package com.busy.honey.stock.investment.stocks.service

import com.busy.honey.stock.investment.stock.service.StockService
import com.busy.honey.stock.investment.stocks.dto.CreateStocksDto
import com.busy.honey.stock.investment.stocks.dto.UpdateStocksDto
import com.busy.honey.stock.investment.stocks.entity.Stocks
import com.busy.honey.stock.investment.stocks.repository.StocksRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class StocksService(private val stocksRepository: StocksRepository,
                    private val stockService: StockService
) {

    fun create(createStocksDto: CreateStocksDto): Stocks {
        val stocks = stocksRepository.save(Stocks(
            stocksId = null,
            stocksName = createStocksDto.stocksName,
            financialStatementsContent = createStocksDto.financialStatementsContent,
            stockShares = 10000,
            createdAt = LocalDateTime.now()
        ))

        stockService.initStocks(
            stocksId = stocks.stocksId!!, price = 1000, amount = 10000, userId = 1L
        )

        return stocks
    }

    @Transactional
    fun update(stocksId: Long, updateStocksDto: UpdateStocksDto): Stocks {
        val optionalStocks = stocksRepository.findById(stocksId)

        if (optionalStocks.isEmpty){
            throw Exception("생성되지 않은 종목을 업데이트 하려고 함")
        }

        val stocks = optionalStocks.get()
        stocks.stocksName = updateStocksDto.stocksName
        stocks.financialStatementsContent = updateStocksDto.financialStatementsContent
        return stocksRepository.save(stocks)
    }

    fun delete(stocksId: Long){
        stocksRepository.deleteById(stocksId)
    }

    fun getStocks(stocksId: Long): Stocks? {
        val optionalStocks = stocksRepository.findById(stocksId)
        if (optionalStocks.isEmpty){
            throw Exception("종목이 없습니다.")
        }

        return optionalStocks.get()
    }


    fun getAllStocks(): List<Stocks>{
        return stocksRepository.findAll()
    }
}