package com.jorgetrujillo.graphqldemo.exceptions

import graphql.ErrorType
import graphql.GraphQLError
import graphql.language.SourceLocation
import java.util.Collections

class ResourceDoesNotExistException(
  private val resourceId: String,
  private val fieldName: String? = null
) : RuntimeException("$resourceId does not exist"), GraphQLError {

  constructor(employeeId: String) : this(employeeId, "employeeId")

  @Suppress("ACCIDENTAL_OVERRIDE")
  override fun getMessage(): String {
    return "Resource with $fieldName $resourceId does not exist"
  }

  override fun getLocations(): List<SourceLocation>? {
    return null
  }

  override fun getErrorType(): ErrorType? {
    return null
  }

  override fun getExtensions(): Map<String, Any> {
    return Collections.singletonMap<String, Any>("invalidField", fieldName)
  }
}
