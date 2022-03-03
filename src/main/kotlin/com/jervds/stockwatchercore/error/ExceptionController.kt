package com.jervds.stockwatchercore.error

import com.jervds.stockwatchercore.model.dto.error.ErrorDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler


@ControllerAdvice
class ExceptionController {

    @ExceptionHandler(CatchableException::class)
    internal fun exception(exception: CatchableException): ResponseEntity<ErrorDto> {
        return ResponseEntity(
            ErrorDto(
                feedbackCode = exception.feedback.name,
                message = exception.feedback.message
            ),
            exception.feedback.status
        )
    }
}