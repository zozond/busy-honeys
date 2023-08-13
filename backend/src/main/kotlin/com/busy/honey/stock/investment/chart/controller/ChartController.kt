package com.busy.honey.stock.investment.chart.controller

import com.busy.honey.stock.investment.chart.dto.ChartDto
import com.busy.honey.stock.investment.chart.service.ChartService
import com.busy.honey.stock.investment.response.RestApiResponse
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/chart")
@CrossOrigin(origins = ["*"], allowedHeaders = ["*"])
class ChartController (private val chartService: ChartService) {

    @GetMapping("/{stocksId}")
    fun getChart(@PathVariable("stocksId") stocksId: Long,
                 @RequestBody chartDto: ChartDto
    ): RestApiResponse{
        val data = mutableMapOf<Any, Any>()
        data["chart"] = chartService.getChart(stocksId, chartDto)
        return RestApiResponse(
            status = "OK",
            description = "",
            data = data
        )
    }

}