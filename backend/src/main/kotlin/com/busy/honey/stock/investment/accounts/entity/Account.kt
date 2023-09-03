package com.busy.honey.stock.investment.accounts.entity

import jakarta.persistence.*

@Entity
@Table(name = "accounts")
class Account(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    var accountId: Long?,
    var money: Long
) {
}