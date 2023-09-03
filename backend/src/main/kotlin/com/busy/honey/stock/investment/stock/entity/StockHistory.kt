package com.busy.honey.stock.investment.stock.entity

import jakarta.persistence.*

@Entity
@Table(name="stock_history")
class StockHistory(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    var stockHistoryId: Long? = null,
    var userId: Long,
    var timestamp: String,
    var price: Int,
    var stocksId: Long,
    var stockAmount: Int,
    var historyType: String
)
