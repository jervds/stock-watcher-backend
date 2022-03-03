package com.jervds.stockwatchercore.service

import com.jervds.stockwatchercore.error.StockWatcherException.PRODUCT_QUANTITY_SHOULD_BE_POSITIVE
import com.jervds.stockwatchercore.error.StockWatcherException.UNKNOWN_PRODUCT
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

    fun patch(id: String, productName: String?, quantityInStock: Int?): Mono<Product> {
        return findById(id)
            .switchIfEmpty(Mono.error(UNKNOWN_PRODUCT.toException()))
            .map { product ->
                quantityInStock?.let { validateStockQuantity(it) }
                product
            }
            .map { product ->
                productName?.let { product.productName = productName }
                quantityInStock?.let { product.quantityInStock = quantityInStock }
                product
            }
            .flatMap(stockRepository::save)
    }

    private fun validateStockQuantity(quantityInStock: Int) {
        if (quantityInStock < 0) throw PRODUCT_QUANTITY_SHOULD_BE_POSITIVE.toException()
    }

}