package com.jervds.stockwatchercore.mapper

import com.jervds.stockwatchercore.model.dto.ProductCreateDto
import com.jervds.stockwatchercore.model.dto.ProductInDto
import com.jervds.stockwatchercore.model.dto.ProductOutDto
import com.jervds.stockwatchercore.model.entity.Product
import org.bson.types.ObjectId

fun Product.toDto() = ProductOutDto(id = "$id", productName = productName)

fun ProductCreateDto.toEntity() = Product(ObjectId(), productName = productName)

fun ProductInDto.toEntity(id: ObjectId) = Product(id, productName = productName)