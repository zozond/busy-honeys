package com.busy.honey.stock.investment

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["com.busy.honey"])
class InvestmentApplication

fun main(args: Array<String>) {
	runApplication<InvestmentApplication>(*args)
}
