package com.jorgetrujillo.graphqlclient.client

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.jorgetrujillo.graphqlclient.domain.GraphQLField
import com.jorgetrujillo.graphqlclient.domain.GraphQLRequest
import com.jorgetrujillo.graphqlclient.domain.GraphQLResponse
import spock.lang.Specification

import javax.net.ssl.SSLSession
import java.net.http.HttpClient
import java.net.http.HttpHeaders
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class GraphQLClientSpec extends Specification {

  GraphQLClient graphQLClient
  String endpoint = 'http://endpoint'

  void setup() {
    graphQLClient = new GraphQLClient(
        endpoint,
        new ObjectMapper().registerModule(new KotlinModule()),
        Mock(HttpClient)
    )
  }

  class Student {
    String name
    String age
  }

  void 'Execute'() {
    given:
    GraphQLRequest graphQLRequest = new GraphQLRequest(
        GraphQLRequest.RequestType.QUERY,
        'students',
        [:],
        [
            new GraphQLField('name', null),
            new GraphQLField('age', null)
        ],
        [:]
    )

    when:
    GraphQLResponse<List<Student>> responseData = graphQLClient.execute(graphQLRequest)

    then:
    1 * graphQLClient.httpClient.send({ HttpRequest httpRequest ->
      assert httpRequest.method() == 'POST'
      assert httpRequest.uri() == new URI(endpoint)
      return true
    },
        HttpResponse.BodyHandlers.ofString()) >> new HttpResponse<String>() {

      @Override
      int statusCode() {
        return 0
      }

      @Override
      HttpRequest request() {
        return null
      }

      @Override
      Optional<HttpResponse<String>> previousResponse() {
        return null
      }

      @Override
      HttpHeaders headers() {
        return null
      }

      @Override
      String body() {
        return getStudentJson()
      }

      @Override
      Optional<SSLSession> sslSession() {
        return null
      }

      @Override
      URI uri() {
        return null
      }

      @Override
      HttpClient.Version version() {
        return null
      }
    }
    0 * _

    responseData.data.students.size() == 2
    responseData.data.students.find { it.name == 'Joe' }.age == 12
  }

  String getStudentJson() {
    return """
{
  "data": {
    "students": [
      {
        "name": "Joe",
        "age": 12
      },
      {
        "name": "Pete",
        "age": 15
       }
    ]
  }
}
"""
  }
}
