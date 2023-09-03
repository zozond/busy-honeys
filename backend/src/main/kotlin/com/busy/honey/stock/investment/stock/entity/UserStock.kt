package com.busy.honey.stock.investment.stock.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "user_stock")
class UserStock(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    var userStockId: Long?,
    var userId: Long,
    var price: Int,
    var stocksId: Long,
    var amount: Int,
    var timestamp: LocalDateTime
)
