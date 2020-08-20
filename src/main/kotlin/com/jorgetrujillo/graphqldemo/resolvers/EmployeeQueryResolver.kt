package com.jorgetrujillo.graphqldemo.resolvers

import com.coxautodev.graphql.tools.GraphQLQueryResolver
import com.jorgetrujillo.graphqldemo.domain.Employee
import com.jorgetrujillo.graphqldemo.domain.Review
import com.jorgetrujillo.graphqldemo.repositories.EmployeeRepository
import com.jorgetrujillo.graphqldemo.repositories.ReviewRepository
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component

@Component
class EmployeeQueryResolver(
    val employeeRepository: EmployeeRepository,
    val reviewRepository: ReviewRepository
) : GraphQLQueryResolver {

  fun employees(): List<Employee> {
    val employees: List<Employee> = employeeRepository.findAll()

    employees.onEach { employee: Employee ->
      employee.reviews = getReviews(employee.employeeId!!)
    }

    return employees
  }

  private fun getReviews(employeeId: String): List<Review> {
    return reviewRepository.findByEmployeeId(employeeId, Pageable.unpaged()).content
  }
}
