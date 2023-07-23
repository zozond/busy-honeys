package com.busy.honey.stock.investment.stock.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name="buyingPrice")
class BuyingPrice(
    @Id
    var buyingPriceId :Long?,
    var price: Int,
    val stocksId: Long,
    var amount: Int
)
