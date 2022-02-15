package com.jervds.stockwatchercore.mapper

import com.jervds.stockwatchercore.model.dto.ProductCreateDto
import com.jervds.stockwatchercore.model.dto.ProductDto
import com.jervds.stockwatchercore.model.entity.Product
import org.bson.types.ObjectId

fun Product.toDto() = ProductDto(id = "$id", productName = productName)

fun ProductCreateDto.toEntity() = Product(ObjectId(), productName = productName)