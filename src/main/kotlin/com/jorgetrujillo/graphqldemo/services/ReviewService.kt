package com.jorgetrujillo.graphqldemo.services

import com.jorgetrujillo.graphqldemo.domain.Review
import com.jorgetrujillo.graphqldemo.domain.ReviewCriteria
import com.jorgetrujillo.graphqldemo.exceptions.ResourceDoesNotExistException
import com.jorgetrujillo.graphqldemo.repositories.EmployeeRepository
import com.jorgetrujillo.graphqldemo.repositories.ReviewRepository
import org.springframework.data.domain.Example
import org.springframework.data.domain.ExampleMatcher
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.ignoreCase
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class ReviewService(
  val reviewRepository: ReviewRepository,
  val employeeRepository: EmployeeRepository
) {

  fun save(review: Review): Review {

    if (employeeRepository.findOneByEmployeeId(review.employeeId!!) == null) {
      throw ResourceDoesNotExistException(review.employeeId, "employeeId")
    }

    return reviewRepository.save(review)
  }

  fun list(reviewCriteria: ReviewCriteria, pageable: Pageable): Page<Review> {

    val matcher: ExampleMatcher = ExampleMatcher.matching()
      .withMatcher("employeeId", ignoreCase())
      .withIgnoreNullValues()

    val reviewExample: Example<Review> = Example.of(
      Review(employeeId = reviewCriteria.employeeId),
      matcher
    )

    return reviewRepository.findAll(reviewExample, pageable)
  }

  fun delete(id: String) {
    reviewRepository.deleteById(id)
  }
}
