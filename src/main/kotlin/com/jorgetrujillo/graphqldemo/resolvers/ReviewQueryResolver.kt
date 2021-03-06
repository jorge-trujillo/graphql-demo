package com.jorgetrujillo.graphqldemo.resolvers

import com.jorgetrujillo.graphqldemo.domain.Review
import com.jorgetrujillo.graphqldemo.domain.ReviewCriteria
import com.jorgetrujillo.graphqldemo.services.ReviewService
import graphql.kickstart.tools.GraphQLQueryResolver
import org.springframework.data.domain.Pageable
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Component

@Component
class ReviewQueryResolver(
  val reviewService: ReviewService
) : GraphQLQueryResolver {

  @PreAuthorize("isAuthenticated")
  fun reviews(employeeId: String): List<Review> {
    return reviewService.list(ReviewCriteria(employeeId = employeeId), Pageable.unpaged()).content
  }
}
