package com.jervds.stockwatchercore.model.entity

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Stock(
        @Id val id: ObjectId,
        val productName: String,
)
