package com.busy.honey.stock.investment.stock.dto

data class BuyStockDto(
    val userId: Long,
    val stocksId: Long,
    val stockAmount: Int,
    val bidPrice: Int
)
