package com.jorgetrujillo.graphqldemo.resolvers

import com.coxautodev.graphql.tools.GraphQLMutationResolver
import com.jorgetrujillo.graphqldemo.domain.Review
import com.jorgetrujillo.graphqldemo.exceptions.ResourceDoesNotExistException
import com.jorgetrujillo.graphqldemo.repositories.EmployeeRepository
import com.jorgetrujillo.graphqldemo.repositories.ReviewRepository
import org.springframework.stereotype.Component

@Component
class ReviewMutationResolver(
    val employeeRepository: EmployeeRepository,
    val reviewRepository: ReviewRepository
) : GraphQLMutationResolver {

  fun createReview(employeeId: String, reviewText: String, rating: Int): Review {
    if (employeeRepository.findOneByEmployeeId(employeeId) == null) {
      throw ResourceDoesNotExistException(employeeId)
    }
    return reviewRepository.save(Review(employeeId, reviewText, rating))
  }

  fun deleteReview(id: String): Boolean {
    reviewRepository.deleteById(id)
    return true
  }
}