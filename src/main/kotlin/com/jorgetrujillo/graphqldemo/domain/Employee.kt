package com.jorgetrujillo.graphqldemo.domain

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

@Document(collection = "employees")
data class Employee(
    @Id
    var id: String? = null,
    val name: String,
    val employeeId: String,
    @CreatedDate
    val created: Instant? = null
) {
  constructor(name: String, employeeId: String) :
      this(null, name, employeeId, null)

  @Transient
  var skills: List<Skill> = mutableListOf()
}
