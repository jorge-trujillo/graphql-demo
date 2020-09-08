package com.jorgetrujillo.graphqldemo.resolvers

import com.coxautodev.graphql.tools.GraphQLMutationResolver
import com.jorgetrujillo.graphqldemo.domain.Review
import com.jorgetrujillo.graphqldemo.services.ReviewService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Component

@Component
class ReviewMutationResolver(
  val reviewService: ReviewService
) : GraphQLMutationResolver {

  @PreAuthorize("hasAnyAuthority('EDIT', 'ADMIN')")
  fun createReview(employeeId: String, reviewText: String, rating: Int): Review {
    return reviewService.save(Review(employeeId, reviewText, rating))
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  fun deleteReview(id: String): Boolean {
    reviewService.delete(id)
    return true
  }
}
