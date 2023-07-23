package com.busy.honey.stock.investment.stocks.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name="stocks")
class Stocks(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    var stocksId: Long?,
    var stocksName: String,
    var financialStatementsContent: String,
    var stockShares: Long,
    var createdAt: LocalDateTime
)