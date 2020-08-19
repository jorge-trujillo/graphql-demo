package com.jorgetrujillo.graphqldemo.resolvers

import com.coxautodev.graphql.tools.GraphQLQueryResolver
import com.jorgetrujillo.graphqldemo.domain.Employee
import com.jorgetrujillo.graphqldemo.domain.Skill
import com.jorgetrujillo.graphqldemo.repositories.EmployeeRepository
import com.jorgetrujillo.graphqldemo.repositories.SkillRepository
import org.springframework.stereotype.Component

@Component
class EmployeeQueryResolver(
    val employeeRepository: EmployeeRepository,
    val skillRepository: SkillRepository
) : GraphQLQueryResolver {

  fun employees(): List<Employee> {
    val employees: List<Employee> = employeeRepository.findAll()

    employees.onEach { employee: Employee ->
      employee.skills = getSkills(employee.employeeId)
    }

    return employees
  }

  private fun getSkills(employeeId: String): List<Skill> {

    return listOf()
  }
}
