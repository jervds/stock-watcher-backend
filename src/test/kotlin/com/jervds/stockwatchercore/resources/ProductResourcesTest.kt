package com.jervds.stockwatchercore.resources

import com.jervds.stockwatchercore.helper.*
import com.jervds.stockwatchercore.model.dto.ProductCreateDto
import com.jervds.stockwatchercore.model.dto.ProductInDto
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

    private fun whenCreatingAProduct(simpleProduct: ProductCreateDto = simpleCreateProductDto()): WebTestClient.ResponseSpec {
        return webTestClient.post()
            .uri("$API$PRODUCT")
            .bodyValue(simpleProduct)
            .exchange()
            .expectStatus().isCreated
    }

    @Test
    fun `create a product in stock should return created product`() {
        whenCreatingAProduct()
                .expectBody<ProductOutDto>()
                .consumeWith { res ->
                    val product = res.responseBody!!
                    assertThat(product.productName).isEqualTo(DEFAULT_PRODUCT_NAME)
                }
    }

    @Test
    fun `create a product in stock should create the product in database`() {
        whenCreatingAProduct()
                .expectBody<ProductOutDto>()
                .consumeWith { res ->
                    val product = res.responseBody!!
                    val productFromDatabase = stockRepository.findById(product.id).block()!!
                    assertThat(product.productName).isEqualTo(productFromDatabase.productName)
                    assertThat(product.id).isEqualTo("${productFromDatabase.id}")
                }
    }

    @Test
    @Disabled
    fun `create should create with a stock to zero`() {
        whenCreatingAProduct()
            .expectBody<ProductOutDto>()
            .consumeWith { res ->
                val product = res.responseBody!!
                val productFromDatabase = stockRepository.findById(product.id).block()!!
                assertThat(product.quantityInStock).isEqualTo(productFromDatabase.quantityInStock)
                assertThat(product.quantityInStock).isEqualTo(0)
            }
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
                    assertThat(product.quantityInStock).isEqualTo(DEFAULT_PRODUCT_QUANTITY)
                    //TODO add stock
                }
    }

    @Test
    @Disabled
    fun `findAll should return a list of product`() {

    }

    private fun whenPatching(simplePatchProductDto: ProductInDto = simplePatchProductDto()): WebTestClient.ResponseSpec {
        return webTestClient.patch()
            .uri("$API$PRODUCT$ID", DEFAULT_PRODUCT_ID)
            .bodyValue(simplePatchProductDto)
            .exchange()
            .expectStatus().isOk
    }

    @Test
    fun `patch should update a product when name is provided`() {
        whenPatching(simplePatchProductDto(productName = "updated product name"))
                .expectBody<ProductOutDto>()
                .consumeWith { res ->
                    val product = res.responseBody!!
                    val productFromDatabase = stockRepository.findById(product.id).block()!!
                    assertThat(product.productName).isEqualTo("updated product name")
                    assertThat(product.productName).isEqualTo(productFromDatabase.productName)
                }
    }

    @Test
    fun `patch should update a product when stock is provided`() {
        whenPatching(simplePatchProductDto(quantityInStock = 10))
            .expectBody<ProductOutDto>()
            .consumeWith { res ->
                val product = res.responseBody!!
                val productFromDatabase = stockRepository.findById(product.id).block()!!
                assertThat(product.quantityInStock).isEqualTo(10)
                assertThat(product.quantityInStock).isEqualTo(productFromDatabase.quantityInStock)
            }
    }

    @Test
    fun `patch should not update product name when name is not provided`() {
        whenPatching(simplePatchProductDto(productName = null))
            .expectBody<ProductOutDto>()
            .consumeWith { res ->
                val product = res.responseBody!!
                val productFromDatabase = stockRepository.findById(product.id).block()!!
                assertThat(product.productName).isEqualTo(DEFAULT_PRODUCT_NAME)
                assertThat(product.productName).isEqualTo(productFromDatabase.productName)
            }
    }

    @Test
    fun `patch should not update stock when stock is not provided`() {
        whenPatching(simplePatchProductDto(quantityInStock = null))
            .expectBody<ProductOutDto>()
            .consumeWith { res ->
                val product = res.responseBody!!
                val productFromDatabase = stockRepository.findById(product.id).block()!!
                assertThat(product.quantityInStock).isEqualTo(DEFAULT_PRODUCT_QUANTITY)
                assertThat(product.quantityInStock).isEqualTo(productFromDatabase.quantityInStock)
            }
    }


    @Test
    @Disabled
    fun `patch should not update a stock bellow zero`() {

    }

    @Test
    @Disabled
    fun `patch should update a stock equal to zero`() {
        whenPatching(simplePatchProductDto(quantityInStock = 0))
            .expectBody<ProductOutDto>()
            .consumeWith { res ->
                val product = res.responseBody!!
                val productFromDatabase = stockRepository.findById(product.id).block()!!
                assertThat(product.quantityInStock).isEqualTo(0)
                assertThat(product.quantityInStock).isEqualTo(productFromDatabase.quantityInStock)
            }
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


}