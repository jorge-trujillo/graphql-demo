package com.jorgetrujillo.graphqldemo.repositories

import com.jorgetrujillo.graphqldemo.domain.Review
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository

interface ReviewRepository : MongoRepository<Review, String> {

  fun findByEmployeeId(employeeId: String, pageable: Pageable): Page<Review>
}