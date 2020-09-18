package com.jorgetrujillo.graphqlclient.domain

import com.jorgetrujillo.graphqlclient.StringTestUtil
import spock.lang.Specification

class GraphQLFieldSpec extends Specification {

  void 'graphql field can be rendered to string'() {
    given:
    GraphQLField graphQLField = new GraphQLField(
        'fieldName',
        null
    )
    String expectedString = 'fieldName'

    when:
    String renderedField = graphQLField.toString()

    then:
    StringTestUtil.requestsEqual(renderedField, expectedString)
  }

  void 'graphql field with sub fields can be rendered to string'() {
    given:
    GraphQLField graphQLField = new GraphQLField(
        'fieldName',
        [
            new GraphQLField('subField', [new GraphQLField('bottomField', null)])
        ]
    )

    String expectedString = """
      fieldName {
        subField {
          bottomField
        }
      }
    """

    when:
    String renderedField = graphQLField.toString()

    then:
    StringTestUtil.requestsEqual(renderedField, expectedString)
  }

}
