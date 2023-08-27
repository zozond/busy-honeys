package com.busy.honey.stock.investment.quote.controller

import com.busy.honey.stock.investment.quote.service.QuoteService
import com.busy.honey.stock.investment.response.RestApiResponse
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/quote")
@CrossOrigin(origins = ["*"], allowedHeaders = ["*"])
class QuoteController(private val quoteService: QuoteService) {

    @GetMapping("/{stocksId}")
    fun getQuote(@PathVariable stocksId: Long): RestApiResponse {
        val data = mutableMapOf<Any, Any>()
        data["currentList"] = quoteService.getQuote(stocksId)
        return RestApiResponse(
            status = "OK",
            description = "",
            data = data
        )
    }

    @GetMapping
    fun getAllQuote(): RestApiResponse {
        val data = mutableMapOf<Any, Any>()
        data["currentList"] = quoteService.getAllQuote()
        return RestApiResponse(
            status = "OK",
            description = "",
            data = data
        )
    }
}