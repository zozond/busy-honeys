package com.busy.honey.stock.investment.accounts.repository

import com.busy.honey.stock.investment.accounts.entity.Account
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AccountsRepository: JpaRepository<Account,Long> {
}