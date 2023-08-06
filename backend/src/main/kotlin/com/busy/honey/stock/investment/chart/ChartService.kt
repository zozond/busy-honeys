package com.busy.honey.stock.investment.chart

import com.busy.honey.stock.investment.chart.dto.ChartDto
import com.busy.honey.stock.investment.stock.repository.StockPriceRepository
import com.busy.honey.stock.investment.utils.Utils
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Service
class ChartService (val stockPriceRepository: StockPriceRepository){

    private fun toLocalDateTime(date: String): LocalDateTime {
        return LocalDateTime.parse("$date 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
    }

    fun getChart(stocksId: Long, chartDto:ChartDto): List<Map<String, Any>>{
        val result = mutableListOf<Map<String, Any>>()

        val from = toLocalDateTime(chartDto.from)
        val to = toLocalDateTime(chartDto.to)

        var startDate = from;

        while(true){

            val endDate = LocalDateTime.parse(chartDto.from + " 23:59:59", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))

            // 종가
            val closeStockPrice = stockPriceRepository.findStockPriceOrderByTimestampDesc(stocksId, true, startDate, endDate, 0, 1)

            // 시가
            val openStockPrice = stockPriceRepository.findStockPriceOrderByTimestampAsc(stocksId,true, startDate, endDate, 0, 1)

            // 최고가
            val highStockPrice = stockPriceRepository.findStockPriceOrderByPriceDesc(stocksId,true, startDate, endDate, 0,1)

            // 최저가
            val lowStockPrice = stockPriceRepository.findStockPriceOrderByPriceAsc(stocksId,true,  startDate, endDate, 0,1)

            // 체결된 거래량
            val volume = stockPriceRepository.countConcludedTrade(stocksId, startDate, endDate)

            if (closeStockPrice.isEmpty() || openStockPrice.isEmpty() || highStockPrice.isEmpty() || lowStockPrice.isEmpty()){
                break
            }

            val item = mutableMapOf<String, Any>()
            item.put("openPrice", openStockPrice.get(0).price.toLong())
            item.put("closePrice", closeStockPrice.get(0).price.toLong())
            item.put("highPrice", highStockPrice.get(0).price.toLong())
            item.put("lowPrice", lowStockPrice.get(0).price.toLong())
            item.put("volume", volume)
            item.put("date", Utils.toDateString(startDate))
            result.add(item)


            if(startDate.compareTo(to) == 0){
                break
            }
            startDate = startDate.plusDays(1)
        }

        return result
    }
}