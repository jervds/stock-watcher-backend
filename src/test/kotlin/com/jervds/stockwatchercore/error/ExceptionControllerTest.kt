package com.jervds.stockwatchercore.error

import com.jervds.stockwatchercore.error.StockWatcherException.UNKNOWN_PRODUCT
import com.jervds.stockwatchercore.model.dto.error.ErrorDto
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class ExceptionControllerTest(
    @Autowired private val exceptionController: ExceptionController
) {

    @Test
    fun `exception controller should return error with provided error code and message`() {
        exceptionController.exception(CatchableException(UNKNOWN_PRODUCT))
            .let {
                assertThat(it.statusCode).isEqualTo(UNKNOWN_PRODUCT.status)
                assertThat(it.body).isExactlyInstanceOf(ErrorDto::class.java)
                val errorDto = it.body!!
                assertThat(errorDto.message).isEqualTo(UNKNOWN_PRODUCT.message)
                assertThat(errorDto.feedbackCode).isEqualTo("UNKNOWN_PRODUCT")
            }

    }

}