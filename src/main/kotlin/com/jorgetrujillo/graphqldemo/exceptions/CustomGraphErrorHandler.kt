package com.jorgetrujillo.graphqldemo.exceptions

import graphql.GraphQLError
import graphql.servlet.GraphQLErrorHandler
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class CustomGraphQLErrorHandler : GraphQLErrorHandler {

  companion object {
    val log: Logger = LoggerFactory.getLogger(this::class.java)
  }

  override fun processErrors(list: List<GraphQLError>): List<GraphQLError> {
    return list.map { error -> GraphQLErrorWrapper(error) }
  }

}