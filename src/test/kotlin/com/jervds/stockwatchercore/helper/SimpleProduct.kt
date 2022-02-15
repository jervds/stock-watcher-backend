package com.jervds.stockwatchercore.helper

import com.jervds.stockwatchercore.model.dto.ProductCreateDto
import com.jervds.stockwatchercore.model.entity.Product
import org.bson.types.ObjectId

const val DEFAULT_PRODUCT_NAME = "sample product"
val DEFAULT_PRODUCT_ID = ObjectId("000000000000000000000001")

fun simpleCreateProductDto(
        productName: String = DEFAULT_PRODUCT_NAME
) = ProductCreateDto(
        productName = productName
)

fun simpleProduct(
        id: ObjectId = ObjectId(),
        productName: String = DEFAULT_PRODUCT_NAME
) = Product(
        id = id,
        productName = productName
)