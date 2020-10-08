package com.jorgetrujillo.graphqlclient.domain

class GraphQLRequestBuilder(
  val name: String,
  val requestType: GraphQLRequest.RequestType
) {

  private val parameters: MutableMap<String, Any> = mutableMapOf()
  private val fields: MutableList<GraphQLField> = mutableListOf()
  private val headers: MutableMap<String, String> = mutableMapOf()

  fun addParameter(name: String, value: Any): GraphQLRequestBuilder {
    parameters[name] = value
    return this
  }

  fun addField(field: GraphQLField): GraphQLRequestBuilder {
    fields.add(field)
    return this
  }

  fun addHeader(name: String, value: String): GraphQLRequestBuilder {
    headers[name] = value
    return this
  }

  fun build(): GraphQLRequest {
    return GraphQLRequest(requestType, name, parameters, fields, headers)
  }
}
