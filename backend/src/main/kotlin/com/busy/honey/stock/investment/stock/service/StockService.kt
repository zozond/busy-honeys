package com.busy.honey.stock.investment.stock.service
import com.busy.honey.stock.investment.stock.dto.BuyStockDto
import com.busy.honey.stock.investment.stock.dto.BuyingPriceDto
import com.busy.honey.stock.investment.stock.dto.SellStockDto
import com.busy.honey.stock.investment.stock.dto.SellingPriceDto
import com.busy.honey.stock.investment.stock.entity.StockPrice
import com.busy.honey.stock.investment.stock.repository.JdslStockPriceRepositoryImpl
import com.busy.honey.stock.investment.stock.repository.StockPriceRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class StockService (private val stockPriceRepository: StockPriceRepository,
                    private val jdslStockPriceRepository: JdslStockPriceRepositoryImpl
){

    @Transactional
    fun changeBuyingPrice(buyingPriceId: Long,
                           buyingPriceDto: BuyingPriceDto
    ): StockPrice{
        val optionalBuyingPrice = stockPriceRepository.findById(buyingPriceId)
        if (optionalBuyingPrice.isEmpty){
            throw Exception("Not Updated Your Asking Price")
        }

        val buyingPrice = optionalBuyingPrice.get()
        buyingPrice.price = buyingPriceDto.bidPrice
        buyingPrice.amount = buyingPriceDto.stockAmount
        stockPriceRepository.save(buyingPrice)
        return buyingPrice
    }

    @Transactional
    fun changeSellingPrice(sellingPriceId: Long,
                           sellingPriceDto: SellingPriceDto
    ): StockPrice{
        val optionalSellingPrice = stockPriceRepository.findById(sellingPriceId)
        if (optionalSellingPrice.isEmpty){
            throw Exception("Not Updated Your Asking Price")
        }

        val sellingPrice = optionalSellingPrice.get()
        sellingPrice.price = sellingPriceDto.askPrice
        sellingPrice.amount = sellingPriceDto.stockAmount
        stockPriceRepository.save(sellingPrice)

        return sellingPrice
    }


    fun buyStock(buyStockDto: BuyStockDto){
        stockPriceRepository.save(
            StockPrice(
                stocksId = buyStockDto.stocksId,
                price = buyStockDto.bidPrice,
                amount = buyStockDto.stockAmount,
                timestamp = LocalDateTime.now(),
                isConcluded = false,
                type = "buy",
                userId = buyStockDto.userId,
                stockPriceId = null
            )
        )
    }

    fun sellStock(sellStockDto: SellStockDto){
        stockPriceRepository.save(
            StockPrice(
                stocksId = sellStockDto.stocksId,
                price = sellStockDto.askPrice,
                amount = sellStockDto.stockAmount,
                timestamp = LocalDateTime.now(),
                isConcluded = false,
                type = "sell",
                userId = sellStockDto.userId,
                stockPriceId = null
            )
        )
    }

    fun initStocks(stocksId: Long, price: Int, amount: Int, userId: Long){
        stockPriceRepository.save(
            StockPrice(
                stocksId = stocksId,
                price = price,
                amount = amount,
                timestamp = LocalDateTime.now(),
                isConcluded = false,
                type = "sell",
                userId = userId,
                stockPriceId = null
            )
        )
    }

    fun getUserOwnStocks(userId: Long): List<StockPrice>{
        return jdslStockPriceRepository.findByUserOwnAllStockPrice(userId)
    }

    fun lastBuyPrice(stocksId: Long, from: LocalDateTime, to: LocalDateTime): Int {
         val result = jdslStockPriceRepository.findByLastPrice(
            isConcluded = true,
            stocksId = stocksId,
            type = "buy",
            from = from,
            to = to,
            limit = 1,
            offset = 0
        )
        if (result.isEmpty()){
            return 1000
        }

        return result[0].price
    }
    fun lastSellPrice(stocksId: Long, from: LocalDateTime, to: LocalDateTime): Int {
        val result = jdslStockPriceRepository.findByLastPrice(
            isConcluded = true,
            stocksId = stocksId,
            type = "sell",
            from = from,
            to = to,
            limit = 1,
            offset = 0
        )
        if (result.isEmpty()){
            return 1000
        }
        return result[0].price
    }

    fun insertSampleData(stocksId: Long, timestamp: LocalDateTime, price:Int, amount: Int) {
        stockPriceRepository.save(
            StockPrice(
                stocksId = stocksId,
                price = price,
                amount = amount,
                timestamp = timestamp,
                isConcluded = true,
                type = "buy",
                userId = 0,
                stockPriceId = null
            )
        )

    }
}