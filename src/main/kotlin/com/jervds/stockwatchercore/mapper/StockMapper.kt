package com.jervds.stockwatchercore.mapper

import com.jervds.stockwatchercore.model.dto.StockDto
import com.jervds.stockwatchercore.model.entity.Stock
import org.bson.types.ObjectId

fun Stock.toDto() = StockDto(id = "$id", productName = productName)

fun StockDto.toEntity() = Stock(id = id?.let { ObjectId(it) } ?: ObjectId(), productName = productName)