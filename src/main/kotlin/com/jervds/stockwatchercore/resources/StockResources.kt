package com.jervds.stockwatchercore.resources

import com.jervds.stockwatchercore.mapper.toDto
import com.jervds.stockwatchercore.mapper.toEntity
import com.jervds.stockwatchercore.model.dto.StockDto
import com.jervds.stockwatchercore.resources.StockResources.Companion.API
import com.jervds.stockwatchercore.service.StockService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping(API)
class StockResources(
        private val stockService: StockService
) {

    @PostMapping(PRODUCT)
    fun create(@RequestBody product: StockDto): Mono<StockDto> = stockService.create(product.toEntity()).map { it.toDto() }

    companion object {
        const val API = "/api"
        const val PRODUCT = "/product"
    }
}