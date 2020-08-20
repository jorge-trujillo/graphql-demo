package com.jorgetrujillo.graphqldemo.services

import com.jorgetrujillo.graphqldemo.domain.Employee
import com.jorgetrujillo.graphqldemo.exceptions.EmployeeAlreadyExistsException
import com.jorgetrujillo.graphqldemo.exceptions.ResourceDoesNotExistException
import com.jorgetrujillo.graphqldemo.repositories.EmployeeRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class EmployeeService(
    val employeeRepository: EmployeeRepository
) {

  fun save(id: String? = null, employee: Employee): Employee {
    if (id == null) {
      if (employeeRepository.findOneByEmployeeId(employee.employeeId!!) != null) {
        throw EmployeeAlreadyExistsException(employee.employeeId!!)
      }
    } else {
      if (employeeRepository.findById(id).orElse(null) == null) {
        throw ResourceDoesNotExistException(id, "id")
      }
      employee.id = id
    }

    return employeeRepository.save(employee)
  }

  fun list(pageable: Pageable): Page<Employee> {
    return employeeRepository.findAll(pageable)
  }

  fun getByEmployeeId(employeeId: String): Employee? {
    return employeeRepository.findOneByEmployeeId(employeeId)
  }

  fun delete(id: String) {
    employeeRepository.deleteById(id)
  }
}