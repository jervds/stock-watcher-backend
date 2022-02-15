package com.jervds.stockwatchercore.resources

import com.jervds.stockwatchercore.model.StockDto
import com.jervds.stockwatchercore.resources.StockResources.Companion.API
import com.jervds.stockwatchercore.resources.StockResources.Companion.TEST
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class StockResourcesTest(
        @Autowired private val webTestClient: WebTestClient
) {
    @Test
    fun `test function should return my dto`() {
        webTestClient.get()
                .uri("$API$TEST")
                .exchange()
                .expectStatus().isOk
                .expectBody<StockDto>()
                .consumeWith { res ->
                    val dto = res.responseBody!!
                    assertThat(dto.stock).isEqualTo(1)
                }
    }
}