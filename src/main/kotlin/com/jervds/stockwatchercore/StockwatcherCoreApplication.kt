package com.jervds.stockwatchercore

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class StockwatcherCoreApplication

fun main(args: Array<String>) {
	runApplication<StockwatcherCoreApplication>(*args)
}
