package com.busy.honey.stock.investment.stock

import com.busy.honey.stock.investment.stock.entity.SellingPrice
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SellingStockRepository: JpaRepository<SellingPrice, Long>{
}