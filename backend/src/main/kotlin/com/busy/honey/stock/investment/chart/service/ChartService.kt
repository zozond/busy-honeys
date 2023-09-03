package com.busy.honey.stock.investment.chart.service

import com.busy.honey.stock.investment.chart.dto.ChartDto
import com.busy.honey.stock.investment.stock.repository.JdslStockPriceRepository
import com.busy.honey.stock.investment.utils.Utils
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Service
class ChartService (val jdslStockPriceRepository: JdslStockPriceRepository){

    private fun toLocalDateTime(date: String): LocalDateTime {
        return LocalDateTime.parse("$date 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
    }

    private fun toDateRangeList(from: String, to: String): List<Pair<LocalDateTime, LocalDateTime>>{
        val result = mutableListOf<Pair<LocalDateTime, LocalDateTime>>();
        val startDate = LocalDate.parse(from, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        val endDate = LocalDate.parse(to, DateTimeFormatter.ofPattern("yyyy-MM-dd")).plusDays(1)
        for (date in startDate.datesUntil(endDate)){
            val start = date.atStartOfDay(); // 2021-10-25 00:00:00.00000000
            val end = date.atTime(LocalTime.MAX) // 2021-1025 23:59:59.999999
            result.add(Pair(start, end))
        }
        return result
    }

    fun getChart(stocksId: Long, chartDto:ChartDto): List<Map<String, Any>>{
        val result = mutableListOf<Map<String, Any>>()

        val dateRangeList = toDateRangeList(chartDto.from, chartDto.to)

        for (pairDate in dateRangeList){
            val startDate = pairDate.first
            val endDate = pairDate.second

            // 종가
            val closeStockPrice = jdslStockPriceRepository.findStockPriceOrderByTimestampDesc(stocksId, true, startDate, endDate, 0, 1)

            // 시가
            val openStockPrice = jdslStockPriceRepository.findStockPriceOrderByTimestampAsc(stocksId,true, startDate, endDate, 0, 1)

            // 최고가
            val highStockPrice = jdslStockPriceRepository.findStockPriceOrderByPriceDesc(stocksId,true, startDate, endDate, 0,1)

            // 최저가
            val lowStockPrice = jdslStockPriceRepository.findStockPriceOrderByPriceAsc(stocksId,true,  startDate, endDate, 0,1)

            // 체결된 거래량
            val volume = jdslStockPriceRepository.countConcludedTrade(stocksId, startDate, endDate)

            val item = mutableMapOf<String, Any>()
            item.put("openPrice", openStockPrice.get(0).price.toLong())
            item.put("closePrice", closeStockPrice.get(0).price.toLong())
            item.put("highPrice", highStockPrice.get(0).price.toLong())
            item.put("lowPrice", lowStockPrice.get(0).price.toLong())
            item.put("volume", volume)
            item.put("date", Utils.toDateString(startDate))

            result.add(item)
        }

        return result
    }
}