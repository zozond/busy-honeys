package com.busy.honey.stock.investment.chart

import com.busy.honey.stock.investment.chart.dto.ChartDto
import com.busy.honey.stock.investment.response.RestApiResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/chart")
class ChartController (val chartService: ChartService)
{

    @GetMapping("/{stockId}")
    fun getChart(@PathVariable("stockId") stockId: Long,
                 @RequestBody chartDto: ChartDto
    ): RestApiResponse{

        chartService.getChart(stockId, chartDto)
        return RestApiResponse(
            status = "OK",
            description = "",
            data = null
        )
    }

}