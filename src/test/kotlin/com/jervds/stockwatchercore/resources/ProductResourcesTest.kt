package com.jervds.stockwatchercore.resources

import com.jervds.stockwatchercore.helper.DEFAULT_PRODUCT_ID
import com.jervds.stockwatchercore.helper.DEFAULT_PRODUCT_NAME
import com.jervds.stockwatchercore.helper.simpleCreateProductDto
import com.jervds.stockwatchercore.helper.simpleProduct
import com.jervds.stockwatchercore.model.dto.ProductOutDto
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
                .expectBody<ProductOutDto>()
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
                .expectBody<ProductOutDto>()
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
                .expectBody<ProductOutDto>()
                .consumeWith { res ->
                    val product = res.responseBody!!
                    assertThat(product.productName).isEqualTo(DEFAULT_PRODUCT_NAME)
                    assertThat(product.id).isEqualTo("$DEFAULT_PRODUCT_ID")
                    //TODO add stock
                }
    }

    @Test
    @Disabled
    fun `findAll should return a list of product`() {

    }

    @Test
    fun `patch should update a product`() {
        webTestClient.patch()
                .uri("$API$PRODUCT$ID", DEFAULT_PRODUCT_ID)
                .bodyValue(simpleCreateProductDto(productName = "updated product name"))
                .exchange()
                .expectStatus().isOk
                .expectBody<ProductOutDto>()
                .consumeWith { res ->
                    val product = res.responseBody!!
                    assertThat(product.productName).isEqualTo("updated product name")
                }
    }

    @Test
    @Disabled
    fun `patch should not update a stock bellow zero`() {

    }

    @Test
    @Disabled
    fun `patch should update only provided fields`() {

    }

    @Test
    @Disabled
    fun `patch should update a stock equal to zero`() {

    }

    @Test
    @Disabled
    fun `create should create with a stock to zero`() {

    }

    @Test
    @Disabled
    fun `patch should return error when id does not exist`() {

    }

    @Test
    @Disabled
    fun `patch should not update when product has been updated in meantime`() {

    }

    @Test
    @Disabled
    fun `patch should update product status to IN_STOCK`() {

    }

    @Test
    @Disabled
    fun `patch should update product status to BEING_REAPPROVISIONNED`() {

    }

    @Test
    @Disabled
    fun `patch should update product status to NO_MORE_SELL`() {

    }

    @Test
    @Disabled
    fun `patch should return error when updating stock when product status is NO_MORE_SELL`() {

    }

    @Test
    @Disabled
    fun `findById should return valid QR code containing product id`() {
    }


    @Test
    @Disabled
    fun `patch should update description`() {
        //TODO create should accept description
        //TODO find should return description
    }

    @Test
    @Disabled
    fun `create should accept an external identifier for the product`() {
        //ex: code barre
    }

    @Test
    @Disabled
    fun `create should fail when external identifier already exists`() {

    }

    @Test
    @Disabled
    fun `create should succeed when external identifier already exists and creation is forced`() {
        //Also check other value for forced if from path
    }

}