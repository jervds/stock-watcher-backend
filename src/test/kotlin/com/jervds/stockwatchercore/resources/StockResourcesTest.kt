package com.jervds.stockwatchercore.resources

import com.jervds.stockwatchercore.model.dto.StockDto
import com.jervds.stockwatchercore.repository.StockRepository
import com.jervds.stockwatchercore.resources.StockResources.Companion.API
import com.jervds.stockwatchercore.resources.StockResources.Companion.PRODUCT
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class StockResourcesTest(
        @Autowired private val webTestClient: WebTestClient,
        @Autowired private val stockRepository: StockRepository
) {

    @BeforeEach
    fun setup() {
        stockRepository.deleteAll().block()
    }

    @Test
    fun `create a product in stock should return created product`() {
        webTestClient.post()
                .uri("$API$PRODUCT")
                .bodyValue(StockDto(id=null,productName = "test product"))
                .exchange()
                .expectStatus().isOk
                .expectBody<StockDto>()
                .consumeWith { res ->
                    val product = res.responseBody!!
                    assertThat(product.productName).isEqualTo("test product")
                }
    }

    @Test
    fun `create a product in stock should create the product in database`() {
        webTestClient.post()
                .uri("$API$PRODUCT")
                .bodyValue(StockDto(id=null,productName = "test product"))
                .exchange()
                .expectStatus().isOk
                .expectBody<StockDto>()
                .consumeWith { res ->
                    val product = res.responseBody!!
                    val productFromDatabase = stockRepository.findById(product.id!!).block()!!
                    assertThat(product.productName).isEqualTo(productFromDatabase.productName)
                    assertThat(product.id).isEqualTo("${productFromDatabase.id}")
                }
    }
}