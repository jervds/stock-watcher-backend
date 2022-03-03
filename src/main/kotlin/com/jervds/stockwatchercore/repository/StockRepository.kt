package com.jervds.stockwatchercore.repository

import com.jervds.stockwatchercore.model.entity.Product
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono


@Repository
interface StockRepository : ReactiveMongoRepository<Product, String> {
    fun findByProductName(productName: String): Mono<Product>
}