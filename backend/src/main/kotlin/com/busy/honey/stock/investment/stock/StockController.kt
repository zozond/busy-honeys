package com.busy.honey.stock.investment.stock

import com.busy.honey.stock.investment.response.RestApiResponse
import com.busy.honey.stock.investment.stock.dto.BuyStockDto
import com.busy.honey.stock.investment.stock.dto.BuyingPriceDto
import com.busy.honey.stock.investment.stock.dto.SellStockDto
import com.busy.honey.stock.investment.stock.dto.SellingPriceDto
import jakarta.websocket.server.PathParam
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/stock")
class StockController (private val stockService: StockService,
                       private val stockHistoryService: StockHistoryService){

    /**
     * 주식 주문 내역 확인
     */
    @GetMapping("/{userId}/history")
    fun getUserHistory(@PathVariable("userId") userId: Long): RestApiResponse{

        val historyList = this.stockHistoryService.getHistory(
            userId = userId,
            limit = 10000,
            offset = 0
        )
        val data = mutableMapOf<Any, Any>()
        data.put("history", historyList)
        return RestApiResponse(
            status = "OK",
            description = "",
            data = data
        )
    }

    /**
     * 주문 매도 내역 정정
     */
    @PutMapping("/{userId}/{sellingPriceId}/sell")
    fun modifyStockSell(@PathVariable("sellingPriceId") sellingPriceId: Long,
                        @PathVariable("userId") userId: Long,
                        @RequestBody sellingPriceDto: SellingPriceDto
    ): RestApiResponse{

        val askingPrice = this.stockService.changeSellingPrice(sellingPriceId, sellingPriceDto)
        this.stockHistoryService.addModifyingSellingStockHistory(sellingPriceId, userId, sellingPriceDto)

        val data = mutableMapOf<Any, Any>()
        data.put("spreadId", sellingPriceId)
        return RestApiResponse(
            status = "OK",
            description = "",
            data = data
        )
    }

    /**
     * 주문 매수 내역 정정
     */
    @PutMapping("/{userId}/{buyingPriceId}/buy")
    fun modifyStockBuy(@PathVariable("buyingPriceId") buyingPriceId: Long,
                       @PathVariable("userId") userId: Long,
                        @RequestBody buyingPriceDto: BuyingPriceDto
    ): RestApiResponse{
        this.stockHistoryService.addModifyingBuyingStockHistory(buyingPriceId, userId, buyingPriceDto)
        val description = this.stockService.changeBuyingPrice(buyingPriceId, buyingPriceDto)
        val data = mutableMapOf<Any, Any>()
        data.put("spreadId", buyingPriceId)
        return RestApiResponse(
            status = "OK",
            description = "",
            data = data
        )
    }

    /**
     * 주식 매수
     */
    @PostMapping("/buy")
    fun buyStock(@RequestBody buyStockDto: BuyStockDto): RestApiResponse {
        this.stockHistoryService.addBuyStockHistory(buyStockDto)

        val data = mutableMapOf<Any, Any>()
        val spreadId = this.stockService.buyStock(buyStockDto)
        data.put("spreadId", spreadId)
        return RestApiResponse(
            status = "OK",
            description = "",
            data = data
        )
    }

    /**
     * 주식 매도
     */
    @PostMapping("/sell")
    fun sellStock(@RequestBody sellStockDto: SellStockDto): RestApiResponse{
        this.stockHistoryService.addSellStockHistory(sellStockDto)

        val data = mutableMapOf<Any, Any>()
        val spreadId = this.stockService.sellStock(sellStockDto)
        data.put("spreadId", spreadId)
        return RestApiResponse(
            status = "OK",
            description = "",
            data = data
        )
    }
}