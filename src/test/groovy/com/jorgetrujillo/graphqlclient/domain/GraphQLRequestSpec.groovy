package com.jorgetrujillo.graphqlclient.domain

import com.jorgetrujillo.graphqlclient.StringTestUtil
import groovy.util.logging.Slf4j
import spock.lang.Specification

@Slf4j
class GraphQLRequestSpec extends Specification {

  void 'Convert to String with simple fields'() {

    given:
    GraphQLRequest graphQLRequest = new GraphQLRequestBuilder('employees', GraphQLRequest.RequestType.QUERY)
        .addParameter('name', 'Joe')
        .addField(new GraphQLField('id', []))
        .addField(new GraphQLField('employeeId', []))
        .addField(new GraphQLField('name', []))
        .build()

    String expected = """
    query {
      employees(name: "Joe") {
        id
        employeeId
        name
      }
    }
"""

    when:
    String query = graphQLRequest.toString()

    then:
    StringTestUtil.requestsEqual(expected, query)
  }

  void 'Convert to String with no parameters and nested fields'() {

    given:
    GraphQLRequest graphQLRequest = new GraphQLRequestBuilder('employees', GraphQLRequest.RequestType.QUERY)
        .addField(new GraphQLField('id', []))
        .addField(new GraphQLField('employeeId', []))
        .addField(new GraphQLField('name', []))
        .addField(new GraphQLField('reviews', [
            new GraphQLField('id', []),
            new GraphQLField('title', [])
        ]))
        .build()

    String expected = """
    query {
      employees {
        id
        employeeId
        name
        reviews {
          id
          title
        }
      }
    }
"""

    when:
    String query = graphQLRequest.toString()

    then:
    StringTestUtil.requestsEqual(expected, query)
  }

}
