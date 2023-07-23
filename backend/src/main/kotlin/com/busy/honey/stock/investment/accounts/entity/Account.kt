package com.busy.honey.stock.investment.accounts.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name="accounts")
class Account (
    @Id
    val accountId: Long?,
    var money: Long
){
}