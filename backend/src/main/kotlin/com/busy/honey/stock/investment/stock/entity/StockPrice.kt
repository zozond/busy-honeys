package com.busy.honey.stock.investment.stock.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "stock_price")
class StockPrice(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    var stockPriceId: Long?,
    var userId: Long,
    var price: Int,
    var stocksId: Long,
    var amount: Int,
    var isConcluded: Boolean,
    var type: String,
    var timestamp: LocalDateTime
)
