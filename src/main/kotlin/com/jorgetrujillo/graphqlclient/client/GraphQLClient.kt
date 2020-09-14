package com.jorgetrujillo.graphqlclient.client

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.jorgetrujillo.graphqlclient.domain.GraphQLRequest
import com.jorgetrujillo.graphqlclient.domain.GraphQLResponse
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

class GraphQLClient(
  private val endpoint: String,
  private val objectMapper: ObjectMapper,
  private val client: OkHttpClient = OkHttpClient()
) {

  private val globalHeaders: MutableMap<String, String> = mutableMapOf()

  fun addGlobalHeader(name: String, value: String) {
    globalHeaders[name] = value
  }

  fun <T> execute(request: GraphQLRequest): GraphQLResponse<T>? {

    val httpRequestBuilder = Request.Builder()
      .url(endpoint)
      .post(request.toJson().toRequestBody("application/json".toMediaType()))

    // Add headers if there are any
    globalHeaders.forEach {
      httpRequestBuilder.header(it.key, it.value)
    }
    request.headers.forEach {
      httpRequestBuilder.header(it.key, it.value)
    }

    // Build request and send it
    val httpRequest = httpRequestBuilder.build()
    val response = client.newCall(httpRequest).execute()

    if (response.isSuccessful && response.body != null) {
      val graphQLResponse = objectMapper.readValue(
        response.body!!.string(),
        object: TypeReference<GraphQLResponse<T>>() {}
      )
      graphQLResponse.statusCode = response.code

      return graphQLResponse
    }

    return GraphQLResponse(response.code, null)
  }
}
