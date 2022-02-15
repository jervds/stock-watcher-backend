package com.jervds.stockwatchercore.service

import com.jervds.stockwatchercore.model.entity.Product
import com.jervds.stockwatchercore.repository.StockRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class StockService(
        private val stockRepository: StockRepository
) {

    fun create(product: Product): Mono<Product> {
        return stockRepository.save(product)
    }

    fun findById(id: String): Mono<Product> {
        return stockRepository.findById(id)
    }

}