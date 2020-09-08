package com.jorgetrujillo.graphqldemo.resolvers

import com.coxautodev.graphql.tools.GraphQLMutationResolver
import com.jorgetrujillo.graphqldemo.domain.Review
import com.jorgetrujillo.graphqldemo.exceptions.ResourceDoesNotExistException
import com.jorgetrujillo.graphqldemo.repositories.EmployeeRepository
import com.jorgetrujillo.graphqldemo.repositories.ReviewRepository
import com.jorgetrujillo.graphqldemo.services.ReviewService
import org.springframework.stereotype.Component

@Component
class ReviewMutationResolver(
    val reviewService: ReviewService
) : GraphQLMutationResolver {

  fun createReview(employeeId: String, reviewText: String, rating: Int): Review {
    return reviewService.save(Review(employeeId, reviewText, rating))
  }

  fun deleteReview(id: String): Boolean {
    reviewService.delete(id)
    return true
  }
}