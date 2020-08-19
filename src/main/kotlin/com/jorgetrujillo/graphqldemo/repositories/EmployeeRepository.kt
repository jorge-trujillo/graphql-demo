package com.jorgetrujillo.graphqldemo.repositories

import com.jorgetrujillo.graphqldemo.domain.Employee
import org.springframework.data.mongodb.repository.MongoRepository

interface EmployeeRepository : MongoRepository<Employee, String>
