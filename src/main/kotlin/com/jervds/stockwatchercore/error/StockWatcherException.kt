package com.jervds.stockwatchercore.error

import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.BAD_REQUEST

enum class StockWatcherException(private val msg: String, private val status: HttpStatus) {
    UNKNOWN_PRODUCT("The product does not exists", BAD_REQUEST),
    PRODUCT_QUANTITY_SHOULD_BE_POSITIVE("The product quantity should be positive", BAD_REQUEST)
    ;

    fun toException() = CatchableException(msg, status)
}