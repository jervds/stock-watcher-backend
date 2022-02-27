package com.jervds.stockwatchercore.model.entity

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Product(
        @Id val id: ObjectId,
        var productName: String,
        var quantityInStock: Int,
)
