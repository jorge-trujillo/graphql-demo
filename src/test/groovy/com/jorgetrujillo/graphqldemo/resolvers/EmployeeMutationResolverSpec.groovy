package com.jorgetrujillo.graphqldemo.resolvers

import com.jorgetrujillo.graphqldemo.domain.Employee
import com.jorgetrujillo.graphqldemo.services.EmployeeService
import spock.lang.Specification

class EmployeeMutationResolverSpec extends Specification {

  EmployeeMutationResolver employeeMutationResolver

  void setup() {
    employeeMutationResolver = new EmployeeMutationResolver(
        Mock(EmployeeService)
    )
  }

  void 'CreateEmployee'() {

    given:
    String name = 'name'
    String employeeId = 'employeeId'
    Employee expected = new Employee()

    when:
    Employee actual = employeeMutationResolver.createEmployee(name, employeeId)

    then:
    1 * employeeMutationResolver.employeeService.save(null, { Employee employee ->
      assert employee.name == name
      assert employee.employeeId == employeeId
      return true
    }) >> expected
    0 * _

    actual.is(expected)
  }

  void 'DeleteEmployee'() {
    given:
    String id = 'id'
    boolean result

    when:
    result = employeeMutationResolver.deleteEmployee(id)

    then:
    1 * employeeMutationResolver.employeeService.delete(id)
    0 * _

    result
  }
}
