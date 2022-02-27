package com.jervds.stockwatchercore

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.config.EnableReactiveMongoAuditing

@SpringBootApplication
@EnableReactiveMongoAuditing
class App

fun main(args: Array<String>) {
	runApplication<App>(*args)
}
