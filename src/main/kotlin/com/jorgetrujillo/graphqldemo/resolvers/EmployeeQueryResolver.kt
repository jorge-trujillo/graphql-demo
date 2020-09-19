package com.jorgetrujillo.graphqldemo.resolvers

import com.coxautodev.graphql.tools.GraphQLQueryResolver
import com.jorgetrujillo.graphqldemo.domain.Employee
import com.jorgetrujillo.graphqldemo.domain.Review
import com.jorgetrujillo.graphqldemo.domain.ReviewCriteria
import com.jorgetrujillo.graphqldemo.services.EmployeeService
import com.jorgetrujillo.graphqldemo.services.ReviewService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Component

@Component
class EmployeeQueryResolver(
  val employeeService: EmployeeService,
  val reviewService: ReviewService
) : GraphQLQueryResolver {

  @PreAuthorize("isAuthenticated()")
  fun employees(): List<Employee> {
    val employees: List<Employee> = employeeService.list(PageRequest.of(0, 100)).content

    GlobalScope.async {

    }
    val deferredRequests = employees.map { employee: Employee ->
      GlobalScope.async {
        employee.reviews = getReviews(employee.employeeId!!)
      }
    }
    runBlocking { deferredRequests.forEach { it.await() } }

    return employees
  }

  private fun getReviews(employeeId: String): List<Review> {
    return reviewService.list(ReviewCriteria(employeeId = employeeId), Pageable.unpaged()).content
  }
}
