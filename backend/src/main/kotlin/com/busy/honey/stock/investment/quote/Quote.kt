package com.busy.honey.stock.investment.quote

data class Quote(
    var stockPriceId :Long,
    var price: Int,
    val stocksId: Long,
    val stocksName: String,
    var amount: Int,
    var type: String
)
