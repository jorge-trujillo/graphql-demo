package com.jorgetrujillo.graphqldemo.exceptions

import graphql.GraphQLError
import spock.lang.Specification

class CustomGraphQLErrorHandlerSpec extends Specification {

  CustomGraphQLErrorHandler customGraphQLErrorHandler = new CustomGraphQLErrorHandler()

  void 'convert to GraphQL errors'() {

    given:
    ResourceDoesNotExistException error = new ResourceDoesNotExistException('Some error')

    when:
    List<GraphQLError> errors = customGraphQLErrorHandler.processErrors([error])

    then:
    errors.every { it instanceof GraphQLErrorWrapper }
  }
}
