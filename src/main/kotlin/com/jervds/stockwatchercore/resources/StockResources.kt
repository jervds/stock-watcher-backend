package com.jervds.stockwatchercore.resources

import com.jervds.stockwatchercore.model.StockDto
import com.jervds.stockwatchercore.resources.StockResources.Companion.API
import com.jervds.stockwatchercore.service.StockService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping(API)
class StockResources(
        private val stockService: StockService
) {

    @GetMapping(TEST)
    fun test(): Mono<StockDto> {
        return stockService.test()
    }

    companion object {
        const val API = "/api"
        const val TEST = "/test"
    }
}