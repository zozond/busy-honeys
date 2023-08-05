package com.busy.honey.stock.investment.stock.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name="stockPrice")
class StockPrice(
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    var stockPriceId :Long?,
    var userId: Long,
    var price: Int,
    val stocksId: Long,
    var amount: Int,
    var isConcluded: Boolean,
    var type: String,
    var timestamp: LocalDateTime
)
