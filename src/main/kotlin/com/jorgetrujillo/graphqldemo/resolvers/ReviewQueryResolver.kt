package com.jorgetrujillo.graphqldemo.resolvers

import com.coxautodev.graphql.tools.GraphQLQueryResolver
import com.jorgetrujillo.graphqldemo.domain.Review
import com.jorgetrujillo.graphqldemo.domain.ReviewCriteria
import com.jorgetrujillo.graphqldemo.services.ReviewService
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component

@Component
class ReviewQueryResolver(
    val reviewService: ReviewService
) : GraphQLQueryResolver {

  fun reviews(employeeId: String): List<Review> {
    return reviewService.list(ReviewCriteria(employeeId = employeeId), Pageable.unpaged()).content
  }
}