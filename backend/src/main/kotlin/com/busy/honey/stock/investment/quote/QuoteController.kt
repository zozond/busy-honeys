package com.busy.honey.stock.investment.quote

import com.busy.honey.stock.investment.response.RestApiResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/quote")
class QuoteController(val quoteService: QuoteService) {
    /**
     * 호가창을 보여줘야 함 Like 경매장 ?
     */

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