package com.jervds.stockwatchercore.error

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler


@ControllerAdvice
class ExceptionController {

    @ExceptionHandler(CatchableException::class)
    internal fun exception(exception: CatchableException): ResponseEntity<Any> {
        return ResponseEntity(exception.msg, exception.code)
    }
}