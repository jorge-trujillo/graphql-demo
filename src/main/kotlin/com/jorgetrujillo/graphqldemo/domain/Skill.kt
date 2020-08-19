package com.jorgetrujillo.graphqldemo.domain

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

@Document(collection = "skills")
data class Skill(
    @Id
    val id: String?,
    val name: String,
    val strength: Int,
    @CreatedDate
    val created: Instant?
) {

  constructor(name: String, strength: Int) :
      this(null, name, strength, null)

}
