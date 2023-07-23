package com.busy.honey.stock.investment.stocks

import com.busy.honey.stock.investment.response.RestApiResponse
import com.busy.honey.stock.investment.stocks.dto.CreateStocksDto
import com.busy.honey.stock.investment.stocks.dto.UpdateStocksDto
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/stocks")
class StocksController(private val stocksService: StocksService) {

    @GetMapping("/{stocksId}")
    fun getStocks(@PathVariable("stocksId") stocksId: Long): RestApiResponse {
        val stocks = this.stocksService.getStocks(stocksId)

        val data = mutableMapOf<Any, Any>()
        data["stocksName"] = stocks!!.stocksName
        data["createdAt"] = stocks.createdAt.toString()
        data["stockShares"] = 0
        data["financialStatementsContent"] = stocks.financialStatementsContent

        return RestApiResponse(
            status = "OK",
            description = "success",
            data = data
        )
    }

    @PostMapping
    fun createStocks(@RequestBody createStocksDto: CreateStocksDto): RestApiResponse {
        val stocks = this.stocksService.create(createStocksDto)
        return RestApiResponse(
            status = "OK",
            description = "success",
            data = null
        )
    }

    @PutMapping("/{stocksId}")
    fun updateStocks(
        @PathVariable("stocksId") stocksId: Long,
        @RequestBody updateStocksDto: UpdateStocksDto
    ): RestApiResponse {
        val stocks = this.stocksService.update(stocksId, updateStocksDto)
        val data = mutableMapOf<Any, Any>()
        data["stocksName"] = stocks.stocksName
        data["createdAt"] = stocks.createdAt.toString()
        data["stockShares"] = stocks.stockShares
        data["financialStatementsContent"] = stocks.financialStatementsContent

        return RestApiResponse(
            status = "OK",
            description = "success",
            data = data
        )
    }

    @DeleteMapping("/{stocksId}")
    fun deleteStocks(@PathVariable("stocksId") stocksId: Long): RestApiResponse {
        this.stocksService.delete(stocksId)
        return RestApiResponse(
            status = "OK",
            description = "success",
            data = null
        )
    }

}