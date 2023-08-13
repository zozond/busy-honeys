package com.busy.honey.stock.investment.stock.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name="stockPrice")
open class StockPrice(
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    open var stockPriceId :Long?,
    open var userId: Long,
    open var price: Int,
    open val stocksId: Long,
    open var amount: Int,
    open var isConcluded: Boolean,
    open var type: String,
    open var timestamp: LocalDateTime
)
