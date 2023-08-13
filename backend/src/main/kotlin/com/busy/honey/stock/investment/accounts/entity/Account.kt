package com.busy.honey.stock.investment.accounts.entity

import jakarta.persistence.*

@Entity
@Table(name="accounts")
open class Account (
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    open val accountId: Long?,
    open var money: Long
){
}