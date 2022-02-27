package com.jervds.stockwatchercore.model.entity

import org.bson.types.ObjectId
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.annotation.Version
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document
data class Product(
    @Id val id: ObjectId,
    var productName: String,
    var quantityInStock: Int,
    @Version var version: Long? = null,
    @CreatedDate val createdDate: LocalDateTime? = null,
    @LastModifiedDate val lastModifiedDate: LocalDateTime? = null
)
