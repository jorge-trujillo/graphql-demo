package com.jorgetrujillo.graphqldemo.services

import com.jorgetrujillo.graphqldemo.domain.Employee
import com.jorgetrujillo.graphqldemo.domain.Review
import com.jorgetrujillo.graphqldemo.exceptions.ResourceDoesNotExistException
import com.jorgetrujillo.graphqldemo.repositories.EmployeeRepository
import com.jorgetrujillo.graphqldemo.repositories.ReviewRepository
import spock.lang.Specification

class ReviewServiceSpec extends Specification {

  ReviewService reviewService

  void setup() {
    reviewService = new ReviewService(Mock(ReviewRepository), Mock(EmployeeRepository))
  }

  void 'create a review'() {

    given:
    Review expected = new Review('id1', 'Great job', 5)

    when:
    Review actual = reviewService.save(expected)

    then:
    1 * reviewService.employeeRepository.findOneByEmployeeId(expected.employeeId) >> new Employee('Joe', 'id1')
    1 * reviewService.reviewRepository.save(expected) >> expected
    0 * _

    actual.is(expected)
  }

  void 'create a review fails if there is no employee'() {

    given:
    Review expected = new Review('id1', 'Great job', 5)

    when:
    reviewService.save(expected)

    then:
    1 * reviewService.employeeRepository.findOneByEmployeeId(expected.employeeId) >> null
    0 * _

    thrown(ResourceDoesNotExistException)
  }

  void 'delete a review'() {

    given:
    String id = 'id1'

    when:
    reviewService.delete(id)

    then:
    1 * reviewService.reviewRepository.deleteById(id)
    0 * _
  }

}
