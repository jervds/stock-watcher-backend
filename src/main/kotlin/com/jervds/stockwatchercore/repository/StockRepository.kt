package com.jervds.stockwatchercore.repository

import com.jervds.stockwatchercore.model.entity.Stock
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono


@Repository
interface StockRepository : ReactiveMongoRepository<Stock, String> {
    fun findByProductName(productName: String): Mono<Stock>
}