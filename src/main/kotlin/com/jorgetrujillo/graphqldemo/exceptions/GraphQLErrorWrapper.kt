package com.jorgetrujillo.graphqldemo.exceptions

import graphql.ErrorClassification
import graphql.GraphQLError
import graphql.language.SourceLocation

class GraphQLErrorWrapper(
  private val error: GraphQLError
) : GraphQLError {

  override fun getMessage(): String? {
    return error.message
  }

  override fun getPath(): MutableList<Any>? {
    return error.path
  }

  override fun getErrorType(): ErrorClassification? {
    return error.errorType
  }

  override fun getLocations(): MutableList<SourceLocation>? {
    return null
  }

  override fun getExtensions(): MutableMap<String, Any>? {
    return error.extensions
  }
}
