package com.jervds.stockwatchercore.service

import com.jervds.stockwatchercore.model.dto.StockDto
import com.jervds.stockwatchercore.model.entity.Stock
import com.jervds.stockwatchercore.repository.StockRepository
import org.bson.types.ObjectId
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class StockService(
        private val stockRepository: StockRepository
) {

    fun create(stock: Stock): Mono<Stock> {
        return stockRepository.save(stock)
    }

}