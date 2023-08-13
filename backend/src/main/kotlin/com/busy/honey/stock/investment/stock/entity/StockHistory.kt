package com.busy.honey.stock.investment.stock.entity

import jakarta.persistence.*

@Entity
@Table(name="stockHistory")
data class StockHistory(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    val stockHistoryId: Long?,
    val userId: Long,
    val timestamp: String,
    val price: Int,
    val stocksId: Long,
    val stockAmount: Int,
    val historyType: String
)
