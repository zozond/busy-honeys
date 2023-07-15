package com.busy.honey.stock.investment.stocks.entity

import java.time.LocalDateTime

class Stocks(
    var stocksId: Long,
    var stocksName: String,
    var financialStatementsContent: String,
    var createdAt: LocalDateTime?
) {

}