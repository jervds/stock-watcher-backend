package com.jervds.stockwatchercore.error

import com.jervds.stockwatchercore.model.dto.error.ErrorDto
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus.UNAUTHORIZED

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class ExceptionControllerTest(
    @Autowired private val exceptionController: ExceptionController
) {

    @Test
    fun `exception controller should return error with provided error code and message`() {
        exceptionController.exception(CatchableException("a message", UNAUTHORIZED))
            .let {
                assertThat(it.statusCode).isEqualTo(UNAUTHORIZED)
                assertThat(it.body).isExactlyInstanceOf(ErrorDto::class.java)
                val errorDto = it.body as ErrorDto
                assertThat(errorDto.message).isEqualTo("a message")
            }

    }

}