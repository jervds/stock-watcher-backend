package com.jervds.stockwatchercore.error

class CatchableException(
    val feedback: StockWatcherException
) : RuntimeException()