package com.jorgetrujillo.graphqldemo.resolvers

import com.coxautodev.graphql.tools.GraphQLMutationResolver
import com.jorgetrujillo.graphqldemo.domain.Employee
import com.jorgetrujillo.graphqldemo.exceptions.EmployeeAlreadyExistsException
import com.jorgetrujillo.graphqldemo.repositories.EmployeeRepository
import com.jorgetrujillo.graphqldemo.services.EmployeeService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Component

@Component
class EmployeeMutationResolver (
    val employeeService: EmployeeService
): GraphQLMutationResolver {

  @PreAuthorize("hasAuthority('ADMIN')")
  fun createEmployee(name:String, employeeId: String) : Employee {
    return employeeService.save(null, Employee(name, employeeId))
  }

  fun deleteEmployee(id: String) : Boolean {
    employeeService.delete(id)
    return true
  }

}