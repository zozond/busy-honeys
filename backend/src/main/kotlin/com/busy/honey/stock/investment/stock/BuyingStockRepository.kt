package com.busy.honey.stock.investment.stock

import com.busy.honey.stock.investment.stock.entity.BuyingPrice
import com.busy.honey.stock.investment.stock.entity.SellingPrice
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BuyingStockRepository: JpaRepository<BuyingPrice, Long>{
}