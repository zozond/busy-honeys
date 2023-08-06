package com.busy.honey.stock.investment.accounts

import com.busy.honey.stock.investment.accounts.dto.DepositDto
import com.busy.honey.stock.investment.accounts.entity.Account
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class AccountsService (val accountsRepository: AccountsRepository){

    fun findById(accountsId: Long): Account?{
        val optionalAccount = accountsRepository.findById(accountsId);
        if (optionalAccount.isEmpty){
            return null
        }
        return optionalAccount.get()
    }

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

    fun deposit(sellerAccountId: Long, buyerAccountId: Long, price: Int){
        val optionalSellerAccount = accountsRepository.findById(sellerAccountId)
        val optionalBuyerAccount = accountsRepository.findById(buyerAccountId)

        if(optionalSellerAccount.isEmpty || optionalBuyerAccount.isEmpty){
            throw Exception("현재 계좌가 존재 하지 않습니다.")
        }
        val buyerAccount = optionalBuyerAccount.get()
        if (buyerAccount.money < price){
            throw Exception("보내는 사람의 현재 계좌에 돈이 없습니다.")
        }

        buyerAccount.money -= price
        accountsRepository.save(buyerAccount)

        val sellerAccount = optionalSellerAccount.get()
        sellerAccount.money += price
        accountsRepository.save(sellerAccount)
    }
}