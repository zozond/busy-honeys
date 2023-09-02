package com.busy.honey.stock.investment.stock.service

import com.busy.honey.stock.investment.stock.dto.BuyStockDto
import com.busy.honey.stock.investment.stock.dto.BuyingPriceDto
import com.busy.honey.stock.investment.stock.dto.SellStockDto
import com.busy.honey.stock.investment.stock.dto.SellingPriceDto
import com.busy.honey.stock.investment.stock.entity.StockPrice
import com.busy.honey.stock.investment.stock.entity.UserStock
import com.busy.honey.stock.investment.stock.repository.JdslStockPriceRepositoryImpl
import com.busy.honey.stock.investment.stock.repository.StockPriceRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.math.RoundingMode
import java.text.DecimalFormat
import java.time.LocalDateTime

@Service
class StockService(
    private val stockPriceRepository: StockPriceRepository,
    private val userStockService: UserStockService,
    private val jdslStockPriceRepository: JdslStockPriceRepositoryImpl
) {

    @Transactional
    fun changeBuyingPrice(
        buyingPriceId: Long,
        buyingPriceDto: BuyingPriceDto
    ): StockPrice {
        val optionalBuyingPrice = stockPriceRepository.findById(buyingPriceId)
        if (optionalBuyingPrice.isEmpty) {
            throw Exception("Not Updated Your Asking Price")
        }

        val buyingPrice = optionalBuyingPrice.get()
        buyingPrice.price = buyingPriceDto.bidPrice
        buyingPrice.amount = buyingPriceDto.stockAmount
        stockPriceRepository.save(buyingPrice)
        return buyingPrice
    }

    @Transactional
    fun changeSellingPrice(
        sellingPriceId: Long,
        sellingPriceDto: SellingPriceDto
    ): StockPrice {
        val optionalSellingPrice = stockPriceRepository.findById(sellingPriceId)
        if (optionalSellingPrice.isEmpty) {
            throw Exception("Not Updated Your Asking Price")
        }

        val sellingPrice = optionalSellingPrice.get()
        sellingPrice.price = sellingPriceDto.askPrice
        sellingPrice.amount = sellingPriceDto.stockAmount
        stockPriceRepository.save(sellingPrice)

        return sellingPrice
    }


    fun buyStock(buyStockDto: BuyStockDto) {
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

    fun sellStock(sellStockDto: SellStockDto) {
        if (userStockService.haveStock(userId = sellStockDto.userId, stocksId = sellStockDto.stocksId)) {
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

    }

    fun initStocks(stocksId: Long, price: Int, amount: Int, userId: Long) {
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

    fun getUserOwnStocks(userId: Long): List<UserStock> {
        return userStockService.getStockList(userId)
//        return jdslStockPriceRepository.findByUserOwnAllStockPrice(userId)
    }

    fun calculateEarningRate(userId: Long): String {
        var totalPrice = 0.0
        var totalCurrentPrice = 0.0
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.HALF_UP

        // 보유 전체 주식 리스트 가져오기
        val list = userStockService.getStockList(userId)
        println(list.size)
        // 순회
        for (item in list) {
            // 매수한 주식 가격
            totalPrice += item.price

            // 최근 체결된 최대 값
            val recentPrices = jdslStockPriceRepository.findByRecentConcluded(item.stocksId, true, 3)
            var max = 0
            for (item in recentPrices){
                max = Math.max(max, item.price)
            }

            totalCurrentPrice += max
        }
        println(totalCurrentPrice)
        println(totalPrice)
        // 수익률 = (현재 주식 가격 / 매수한 주식 가격) * 100 - 100
        val result = df.format(((totalCurrentPrice / totalPrice) * 100.0) - 100.0)
        if (totalPrice == 0.0) {
            return "0.0"
        } else {
            return result
        }
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
        if (result.isEmpty()) {
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
        if (result.isEmpty()) {
            return 1000
        }
        return result[0].price
    }

    fun insertSampleData(stocksId: Long, timestamp: LocalDateTime, price: Int, amount: Int) {
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