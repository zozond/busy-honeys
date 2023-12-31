package com.busy.honey.stock.investment.stock.service

import com.busy.honey.stock.investment.stock.dto.BuyStockDto
import com.busy.honey.stock.investment.stock.dto.BuyingPriceDto
import com.busy.honey.stock.investment.stock.dto.SellStockDto
import com.busy.honey.stock.investment.stock.dto.SellingPriceDto
import com.busy.honey.stock.investment.stock.entity.StockHistory
import com.busy.honey.stock.investment.stock.repository.StockHistoryRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class StockHistoryService (val stockHistoryRepository: StockHistoryRepository){

    fun getHistory(userId: Long, limit: Int, offset: Int): List<StockHistory>{
        return stockHistoryRepository.findByUserId(userId, PageRequest.of(offset, limit, Sort.by("timestamp").descending()))
    }
    fun addBuyStockHistory(buyStockDto: BuyStockDto){
        stockHistoryRepository.save(
            StockHistory(
                stocksId = buyStockDto.stocksId,
                stockAmount = buyStockDto.stockAmount,
                price = buyStockDto.bidPrice,
                userId = buyStockDto.userId,
                historyType = "sell",
                timestamp = LocalDateTime.now().toString()
            )
        )
    }

    fun addSellStockHistory(sellStockDto: SellStockDto){
        stockHistoryRepository.save(
            StockHistory(
                stocksId = sellStockDto.stocksId,
                stockAmount = sellStockDto.stockAmount,
                price = sellStockDto.askPrice,
                userId = sellStockDto.userId,
                historyType = "sell",
                timestamp = LocalDateTime.now().toString()
            )
        )
    }


    fun addModifyingSellingStockHistory(sellingStockId: Long, userId: Long, sellingPriceDto: SellingPriceDto){
        val optionalSellingStock = stockHistoryRepository.findById(sellingStockId)
        if (optionalSellingStock.isEmpty){
            return
        }

        val sellingStock = optionalSellingStock.get()
        stockHistoryRepository.save(
            StockHistory(
                stocksId = sellingStock.stocksId,
                stockAmount = sellingStock.stockAmount,
                price = sellingStock.price,
                userId = userId,
                historyType = "sell",
                timestamp = LocalDateTime.now().toString()
            )
        )
    }

    fun addModifyingBuyingStockHistory(buyingStockId: Long, userId: Long, buyingPriceDto: BuyingPriceDto){
        val optionalBuyingStock = stockHistoryRepository.findById(buyingStockId)
        if (optionalBuyingStock.isEmpty){
            return
        }

        val buyingStock = optionalBuyingStock.get()
        stockHistoryRepository.save(
            StockHistory(
                stocksId = buyingStock.stocksId,
                stockAmount = buyingStock.stockAmount,
                price = buyingStock.price,
                userId = userId,
                historyType = "sell",
                timestamp = LocalDateTime.now().toString()
            )
        )
    }
}