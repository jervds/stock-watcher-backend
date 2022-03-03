package com.jervds.stockwatchercore.repository

import com.jervds.stockwatchercore.helper.DEFAULT_PRODUCT_ID
import com.jervds.stockwatchercore.helper.simpleProduct
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatCode
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime.now

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class StockRepositoryTest(
    @Autowired val stockRepository: StockRepository
) {
    @BeforeEach
    fun setup() {
        stockRepository.deleteAll().block()
        stockRepository.save(simpleProduct(id = DEFAULT_PRODUCT_ID)).block()
    }

    @Test
    fun `optimistic locking should be activated`() {
        val aProduct1 = stockRepository.findById("$DEFAULT_PRODUCT_ID").block()!!
        val aProduct2 = stockRepository.findById("$DEFAULT_PRODUCT_ID").block()!!
        assertThatCode { stockRepository.save(aProduct1).block() }.doesNotThrowAnyException()
        assertThatCode {
            stockRepository.save(aProduct2).block()
        }.hasMessageContaining("Optimistic lock exception on saving entity")

    }

    @Test
    fun `audit should be activated`() {
        val product = stockRepository.findById("$DEFAULT_PRODUCT_ID").block()!!
        assertThat(product.createdDate).isBefore(now())
        assertThat(product.lastModifiedDate).isBefore(now())
    }
}