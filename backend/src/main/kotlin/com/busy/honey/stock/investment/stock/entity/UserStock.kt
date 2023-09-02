package com.busy.honey.stock.investment.stock.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name="userStock")
open class UserStock(
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    open var userStockId :Long?,
    open var userId: Long,
    open var price: Int,
    open val stocksId: Long,
    open var amount: Int,
    open var timestamp: LocalDateTime
)
