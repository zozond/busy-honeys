package com.busy.honey.stock.investment.quote

import com.busy.honey.stock.investment.stock.repository.StockPriceRepository
import com.busy.honey.stock.investment.stocks.StocksRepository
import com.busy.honey.stock.investment.utils.Utils
import org.springframework.stereotype.Service

@Service
class QuoteService (val stockPriceRepository: StockPriceRepository,
                    val stocksRepository: StocksRepository){

    fun getQuote(stocksId: Long): List<Quote>{
        val result = mutableListOf<Quote>()

        val from = Utils.getTodayStartDateTime()
        val to = Utils.getTodayEndDateTime()
        val stockList = stockPriceRepository.findStockPriceForQuote(stocksId = stocksId, from=from, to=to, offset=0, limit=10000)

        for (stockPrice in stockList){
            val stocks = stocksRepository.findById(stockPrice.stocksId).get()
            result.add(
                Quote(
                    stockPriceId = stockPrice.stockPriceId!!,
                    stocksName = stocks.stocksName,
                    stocksId = stockPrice.stocksId,
                    amount = stockPrice.amount,
                    price = stockPrice.price,
                    type = stockPrice.type
                )
            )
        }

        return result
    }

    fun getAllQuote(): List<Quote>{
        val result = mutableListOf<Quote>()

        val from = Utils.getTodayStartDateTime()
        val to = Utils.getTodayEndDateTime()
        val stockList = stockPriceRepository.findStockPriceForAllQuote(from=from, to=to, offset=0, limit=10000)

        for (stockPrice in stockList){
            val stocks = stocksRepository.findById(stockPrice.stocksId).get()
            result.add(
                Quote(
                    stockPriceId = stockPrice.stockPriceId!!,
                    stocksName = stocks.stocksName,
                    stocksId = stockPrice.stocksId,
                    amount = stockPrice.amount,
                    price = stockPrice.price,
                    type = stockPrice.type
                )
            )
        }

        return result
    }
}