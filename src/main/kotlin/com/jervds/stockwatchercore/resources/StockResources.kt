package com.jervds.stockwatchercore.resources

import com.jervds.stockwatchercore.mapper.toDto
import com.jervds.stockwatchercore.mapper.toEntity
import com.jervds.stockwatchercore.model.dto.ProductCreateDto
import com.jervds.stockwatchercore.model.dto.ProductDto
import com.jervds.stockwatchercore.resources.StockResources.Companion.API
import com.jervds.stockwatchercore.service.StockService
import org.springframework.http.HttpStatus.CREATED
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
@RequestMapping(API)
class StockResources(
        private val stockService: StockService
) {

    @PostMapping(PRODUCT)
    @ResponseStatus(CREATED)
    fun create(@RequestBody product: ProductCreateDto): Mono<ProductDto> = stockService.create(product.toEntity()).map { it.toDto() }

    @GetMapping("$PRODUCT$ID")
    fun findById(@PathVariable id: String): Mono<ProductDto> = stockService.findById(id).map { it.toDto() }

    companion object {
        const val API = "/api"
        const val PRODUCT = "/product"
        const val ID = "/{id}"
    }
}