package com.jervds.stockwatchercore.error

import org.springframework.http.HttpStatus

class CatchableException(
    val msg: String,
    val code: HttpStatus,
) : RuntimeException()