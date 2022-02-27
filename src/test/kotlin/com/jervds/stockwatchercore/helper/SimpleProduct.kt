package com.jervds.stockwatchercore.helper

import com.jervds.stockwatchercore.model.dto.ProductCreateDto
import com.jervds.stockwatchercore.model.dto.ProductInDto
import com.jervds.stockwatchercore.model.entity.Product
import org.bson.types.ObjectId

const val DEFAULT_PRODUCT_NAME = "sample product"
val DEFAULT_PRODUCT_ID = ObjectId("000000000000000000000001")
const val DEFAULT_PRODUCT_QUANTITY = 5

fun simpleCreateProductDto(
        productName: String = DEFAULT_PRODUCT_NAME
) = ProductCreateDto(
        productName = productName
)

fun simplePatchProductDto(
        productName: String? = DEFAULT_PRODUCT_NAME,
        quantityInStock: Int? = DEFAULT_PRODUCT_QUANTITY,
) = ProductInDto(
        productName = productName,
        quantityInStock = quantityInStock,
)

fun simpleProduct(
        id: ObjectId = ObjectId(),
        productName: String = DEFAULT_PRODUCT_NAME,
        stock: Int = DEFAULT_PRODUCT_QUANTITY
) = Product(
        id = id,
        productName = productName,
        quantityInStock = stock,
)