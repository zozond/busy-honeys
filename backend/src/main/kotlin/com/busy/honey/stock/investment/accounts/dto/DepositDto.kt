package com.busy.honey.stock.investment.accounts.dto

data class DepositDto(
    val fromAccountId: Long,
    val price: Int,
    val toAccountId: Long)
