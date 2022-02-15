package com.jervds.stockwatchercore.service

import com.jervds.stockwatchercore.model.StockDto
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class StockService {

    fun test() = Mono.just(StockDto(stock =1))
}