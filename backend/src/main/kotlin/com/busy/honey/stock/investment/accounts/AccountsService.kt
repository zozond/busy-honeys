package com.busy.honey.stock.investment.accounts

import com.busy.honey.stock.investment.accounts.dto.DepositDto
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class AccountsService (val accountsRepository: AccountsRepository){

    @Transactional
    fun deposit(depositDto: DepositDto){
        val fromAccountId = depositDto.fromAccountId
        val toAccountId = depositDto.toAccountId
        val price = depositDto.price

        val optionalFromAccount = accountsRepository.findById(fromAccountId)
        val optionalToAccount = accountsRepository.findById(toAccountId)

        if(optionalFromAccount.isEmpty || optionalToAccount.isEmpty){
            throw Exception("현재 계좌가 존재 하지 않습니다.")
        }
        val fromAccount = optionalFromAccount.get()
        if (fromAccount.money < price){
            throw Exception("보내는 사람의 현재 계좌에 돈이 없습니다.")
        }

        fromAccount.money -= price
        accountsRepository.save(fromAccount)

        val toAccount = optionalToAccount.get()
        toAccount.money += price
        accountsRepository.save(toAccount)
    }
}