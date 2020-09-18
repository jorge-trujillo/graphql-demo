package com.jorgetrujillo.graphqlclient.client

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.jorgetrujillo.graphqlclient.domain.GraphQLRequest
import com.jorgetrujillo.graphqlclient.domain.GraphQLResponse
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class GraphQLClient(
  private val endpoint: String,
  private val objectMapper: ObjectMapper,
  private val client: HttpClient = HttpClient.newHttpClient()
) {

  private val globalHeaders: MutableMap<String, String> = mutableMapOf()

  fun addGlobalHeader(name: String, value: String) {
    globalHeaders[name] = value
  }

  fun <T> execute(request: GraphQLRequest): GraphQLResponse<T>? {

    val httpRequestBuilder = HttpRequest.newBuilder()
      .uri(URI.create(endpoint))
      .POST(HttpRequest.BodyPublishers.ofString(request.toJson()))

    // Add headers if there are any
    globalHeaders.forEach {
      httpRequestBuilder.header(it.key, it.value)
    }
    request.headers.forEach {
      httpRequestBuilder.header(it.key, it.value)
    }

    // Build request and send it
    val httpRequest = httpRequestBuilder.build()
    val response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString())

    if (response.statusCode() == 200 && response.body() != null) {
      val graphQLResponse = objectMapper.readValue(
        response.body(),
        object : TypeReference<GraphQLResponse<T>>() {}
      )
      graphQLResponse.statusCode = response.statusCode()

      return graphQLResponse
    }

    return GraphQLResponse(response.statusCode(), null)
  }
}
