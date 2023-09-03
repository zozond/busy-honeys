package com.busy.honey.stock.investment

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
@EnableJpaAuditing
@EnableAutoConfiguration
class InvestmentApplication

fun main(args: Array<String>) {
	runApplication<InvestmentApplication>(*args)
}
