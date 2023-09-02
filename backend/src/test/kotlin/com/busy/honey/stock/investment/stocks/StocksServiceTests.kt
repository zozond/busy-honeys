package com.busy.honey.stock.investment.stocks

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class StocksServiceTests {
    @BeforeEach
    fun setup() {
    }

    @Test
    fun a(){
        val from = "2023-08-01"
        val to = "2023-09-02"
        val startDate = LocalDate.parse(from, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        val endDate = LocalDate.parse(to, DateTimeFormatter.ofPattern("yyyy-MM-dd")).plusDays(1)
        for (date in startDate.datesUntil(endDate)){
            val start = date.atStartOfDay(); // 2021-10-25 00:00:00.00000000
            val end = date.atTime(LocalTime.MAX) // 2021-1025 23:59:59.999999
            println("$start, $end")
        }

    }
//
//    @Test
//    @DisplayName("종목 가져오기 테스트")
//    fun findUserTest() {
//        val stocksId = 0L
//        val stocks = Stocks(
//            stocksId = stocksId,
//            stocksName = "테스트",
//            financialStatementsContent = "재무제표",
//            createdAt = LocalDateTime.now()
//        )
//        stocksService.repository[stocksId] = stocks
//        Assertions.assertEquals(stocks, stocksService.getStocks(stocksId))
//    }
//
//    @Test
//    @DisplayName("종목 생성 테스트")
//    fun createTest() {
//        val stocksName = "종목!!!"
//        val financialStatementsContent = "재무제표"
//        val createStocksDto = CreateStocksDto(
//            stocksName = stocksName,
//            financialStatementsContent = financialStatementsContent
//        )
//
//        val stocks = stocksService.create(createStocksDto)
//        val stocksId = stocks.stocksId
//        Assertions.assertEquals(stocks, stocksService.getStocks(stocksId))
//    }
//
//    @Test
//    @DisplayName("종목 삭제 테스트")
//    fun deleteTest() {
//        val stocksId = 0L
//        val stocks = Stocks(
//            stocksId = stocksId,
//            stocksName = "테스트",
//            financialStatementsContent = "재무제표",
//            createdAt = LocalDateTime.now()
//        )
//        stocksService.repository[stocksId] = stocks
//        Assertions.assertEquals(stocks, stocksService.getStocks(stocksId))
//
//        stocksService.delete(stocksId)
//        Assertions.assertNull(stocksService.getStocks(stocksId))
//    }
//
//    @Test
//    @DisplayName("종목 정보 변경 테스트")
//    fun updateTest() {
//        val stocksName = "종목1"
//        val financialStatementsContent = "재무제표"
//        val createStocksDto = CreateStocksDto(
//            stocksName = stocksName,
//            financialStatementsContent = financialStatementsContent
//        )
//
//        val stocks = stocksService.create(createStocksDto)
//        val stocksId = stocks.stocksId
//        Assertions.assertEquals(stocks, stocksService.getStocks(stocksId))
//
//        val newStocksName = "종목2"
//        val newFinancialStatementsContent = "재무제표2"
//        val updateStocksDto = UpdateStocksDto(
//            financialStatementsContent = newFinancialStatementsContent,
//            stocksName = newStocksName
//        )
//        Assertions.assertNotEquals(stocks, stocksService.update(stocksId, updateStocksDto))
//    }
//
//    @Test
//    @DisplayName("종목 정보 변경 테스트, 생성되지 않은 종목을 업데이트 한 경우")
//    fun updateTest2() {
//        val stocksId = 0L
//        val newStocksName = "종목2"
//        val newFinancialStatementsContent = "재무제표2"
//        val updateStocksDto = UpdateStocksDto(
//            financialStatementsContent = newFinancialStatementsContent,
//            stocksName = newStocksName
//        )
//        Assertions.assertThrows(Exception::class.java) { stocksService.update(stocksId, updateStocksDto) }
//    }

}