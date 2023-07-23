package com.busy.honey.stock.investment.stock
import com.busy.honey.stock.investment.stock.dto.BuyStockDto
import com.busy.honey.stock.investment.stock.dto.BuyingPriceDto
import com.busy.honey.stock.investment.stock.dto.SellStockDto
import com.busy.honey.stock.investment.stock.dto.SellingPriceDto
import com.busy.honey.stock.investment.stock.entity.BuyingPrice
import com.busy.honey.stock.investment.stock.entity.SellingPrice
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class StockService (val sellingStockRepository: SellingStockRepository,
                    val buyingStockRepository: BuyingStockRepository){

    @Transactional
    fun changeBuyingPrice(buyingPriceId: Long,
                           buyingPriceDto: BuyingPriceDto
    ): String{
        val optionalBuyingPrice = buyingStockRepository.findById(buyingPriceId)
        if (optionalBuyingPrice.isEmpty){
            return "Not Updated Your Buying Price"
        }

        val buyingPrice = optionalBuyingPrice.get()
        buyingPrice.price = buyingPriceDto.bidPrice
        buyingPrice.amount = buyingPriceDto.stockAmount
        buyingStockRepository.save(buyingPrice)
        return "OK"
    }

    @Transactional
    fun changeSellingPrice(sellingPriceId: Long,
                           sellingPriceDto: SellingPriceDto
    ): SellingPrice{
        val optionalSellingPrice = sellingStockRepository.findById(sellingPriceId)
        if (optionalSellingPrice.isEmpty){
            throw Exception("Not Updated Your Asking Price")
        }

        val sellingPrice = optionalSellingPrice.get()
        sellingPrice.price = sellingPriceDto.askPrice
        sellingPrice.amount = sellingPriceDto.stockAmount
        sellingStockRepository.save(sellingPrice)

        return sellingPrice
    }


    fun buyStock(buyStockDto: BuyStockDto){
        buyingStockRepository.save(
            BuyingPrice(
                stocksId = buyStockDto.stocksId,
                buyingPriceId = null,
                price = buyStockDto.bidPrice,
                amount = buyStockDto.stockAmount
            )
        )
    }

    fun sellStock(sellStockDto: SellStockDto){
        sellingStockRepository.save(SellingPrice(
            stocksId = sellStockDto.stocksId,
            sellingPriceId = null,
            price = sellStockDto.askPrice,
            amount = sellStockDto.stockAmount
        ))
    }

}