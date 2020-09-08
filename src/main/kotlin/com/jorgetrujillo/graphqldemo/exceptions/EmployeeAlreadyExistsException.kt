package com.jorgetrujillo.graphqldemo.exceptions

import graphql.ErrorType
import graphql.GraphQLError
import graphql.language.SourceLocation
import java.util.Collections

class EmployeeAlreadyExistsException(
  private val employeeId: String) : RuntimeException("$employeeId already exists"), GraphQLError {

  companion object {
    const val FIELD_NAME = "employeeId"
  }

  @Suppress("ACCIDENTAL_OVERRIDE")
  override fun getMessage(): String {
    return "Employee id $employeeId already exists and cannot be created again"
  }

  override fun getLocations(): List<SourceLocation>? {
    return null
  }

  override fun getErrorType(): ErrorType? {
    return null
  }

  override fun getExtensions(): Map<String, Any> {
    return Collections.singletonMap<String, Any>("invalidField", FIELD_NAME)
  }
}
