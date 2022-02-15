package com.jervds.stockwatchercore.resources

import com.jervds.stockwatchercore.helper.DEFAULT_PRODUCT_ID
import com.jervds.stockwatchercore.helper.DEFAULT_PRODUCT_NAME
import com.jervds.stockwatchercore.helper.simpleCreateProductDto
import com.jervds.stockwatchercore.helper.simpleProduct
import com.jervds.stockwatchercore.model.dto.ProductDto
import com.jervds.stockwatchercore.repository.StockRepository
import com.jervds.stockwatchercore.resources.StockResources.Companion.API
import com.jervds.stockwatchercore.resources.StockResources.Companion.ID
import com.jervds.stockwatchercore.resources.StockResources.Companion.PRODUCT
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class ProductResourcesTest(
        @Autowired private val webTestClient: WebTestClient,
        @Autowired private val stockRepository: StockRepository
) {

    @BeforeEach
    fun setup() {
        stockRepository.deleteAll().block()
        stockRepository.save(simpleProduct(id = DEFAULT_PRODUCT_ID)).block()
    }

    @Test
    fun `create a product in stock should return created product`() {
        webTestClient.post()
                .uri("$API$PRODUCT")
                .bodyValue(simpleCreateProductDto())
                .exchange()
                .expectStatus().isCreated
                .expectBody<ProductDto>()
                .consumeWith { res ->
                    val product = res.responseBody!!
                    assertThat(product.productName).isEqualTo(DEFAULT_PRODUCT_NAME)
                }
    }

    @Test
    fun `create a product in stock should create the product in database`() {
        webTestClient.post()
                .uri("$API$PRODUCT")
                .bodyValue(simpleCreateProductDto())
                .exchange()
                .expectStatus().isCreated
                .expectBody<ProductDto>()
                .consumeWith { res ->
                    val product = res.responseBody!!
                    val productFromDatabase = stockRepository.findById(product.id).block()!!
                    assertThat(product.productName).isEqualTo(productFromDatabase.productName)
                    assertThat(product.id).isEqualTo("${productFromDatabase.id}")
                }
    }

    @Test
    fun `findById should return the product`() {
        webTestClient.get()
                .uri("$API$PRODUCT$ID", DEFAULT_PRODUCT_ID)
                .exchange()
                .expectStatus().isOk
                .expectBody<ProductDto>()
                .consumeWith { res ->
                    val product = res.responseBody!!
                    assertThat(product.productName).isEqualTo(DEFAULT_PRODUCT_NAME)
                    assertThat(product.id).isEqualTo("$DEFAULT_PRODUCT_ID")
                }
    }

    @Test
    @Disabled
    fun `findAll should return a list of product`() {

    }
}