package com.busy.honey.stock.investment

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling


@SpringBootApplication(scanBasePackages = ["com.busy.honey"])
@EnableScheduling
class InvestmentApplication

fun main(args: Array<String>) {
	runApplication<InvestmentApplication>(*args)
}
