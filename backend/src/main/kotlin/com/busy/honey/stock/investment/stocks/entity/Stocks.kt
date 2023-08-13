package com.busy.honey.stock.investment.stocks.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name="stocks")
open class Stocks(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    open var stocksId: Long?,
    open var stocksName: String,
    open var financialStatementsContent: String,
    open var stockShares: Long,
    open var createdAt: LocalDateTime
)