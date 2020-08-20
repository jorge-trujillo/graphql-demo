package com.jorgetrujillo.graphqldemo.resolvers

import com.coxautodev.graphql.tools.GraphQLQueryResolver
import com.jorgetrujillo.graphqldemo.domain.Review
import com.jorgetrujillo.graphqldemo.repositories.ReviewRepository
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component

@Component
class ReviewQueryResolver(
    val reviewRepository: ReviewRepository
) : GraphQLQueryResolver {

  fun reviews(employeeId: String): List<Review> {
    return reviewRepository.findByEmployeeId(employeeId, Pageable.unpaged()).content
  }
}