package com.busy.honey.stock.investment.stock.repository

import com.busy.honey.stock.investment.stock.entity.UserStock
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserStockRepository: JpaRepository<UserStock, Long> {
}