package com.jorgetrujillo.graphqldemo.domain

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

@Document(collection = "reviews")
data class Review(
    @Id
    var id: String? = null,
    val employeeId: String? = null,
    val reviewText: String? = null,
    val rating: Int? = null,
    @CreatedDate
    val created: Instant? = null
) {

  constructor(employeeId: String, reviewText: String, rating: Int) :
      this(null, employeeId, reviewText, rating, null)
}
