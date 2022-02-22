package com.jervds.stockwatchercore.resources

import com.jervds.stockwatchercore.mapper.toDto
import com.jervds.stockwatchercore.mapper.toEntity
import com.jervds.stockwatchercore.model.dto.ProductCreateDto
import com.jervds.stockwatchercore.model.dto.ProductInDto
import com.jervds.stockwatchercore.model.dto.ProductOutDto
import com.jervds.stockwatchercore.resources.StockResources.Companion.API
import com.jervds.stockwatchercore.service.StockService
import org.bson.types.ObjectId
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
    fun create(@RequestBody product: ProductCreateDto): Mono<ProductOutDto> = stockService.create(product.toEntity()).map { it.toDto() }

    @GetMapping("$PRODUCT$ID")
    fun findById(@PathVariable id: String): Mono<ProductOutDto> = stockService.findById(id).map { it.toDto() }

    @PatchMapping("$PRODUCT$ID")
    fun put(@PathVariable id: String, @RequestBody product: ProductInDto): Mono<ProductOutDto> = stockService.put(product.toEntity(ObjectId(id))).map { it.toDto() }

    companion object {
        const val API = "/api"
        const val PRODUCT = "/product"
        const val ID = "/{id}"
    }
}