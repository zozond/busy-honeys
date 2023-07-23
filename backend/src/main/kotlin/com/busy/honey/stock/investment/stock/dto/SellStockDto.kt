package com.busy.honey.stock.investment.stock.dto

data class SellStockDto(
    val userId: Long,
    val stocksId: Long,
    val stockAmount: Int,
    val askPrice: Int
)
