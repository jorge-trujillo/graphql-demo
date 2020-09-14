package com.jorgetrujillo.graphqlclient.domain

import com.fasterxml.jackson.databind.ObjectMapper

data class GraphQLRequest(
  val requestType: RequestType,
  val name: String,
  val parameters: Map<String, Object>,
  val fields: List<GraphQLField>,
  val headers: Map<String, String>
) {

  enum class RequestType {
    QUERY, MUTATION
  }

  override fun toString(): String {
    val parameters = if (parameters.isNotEmpty())
      "(${parameters.map { it.key + ": " + '"' + it.value + '"'}.joinToString( separator = ",")})"
    else ""

    return """
      ${requestType.toString().toLowerCase()} {
        ${name}$parameters {
          ${fields.map { it.toString() }.joinToString ( separator = "\n" )}
      }
    }
    """
  }

  fun toJson(): String {
    val jsonObject = mapOf<String, String>("query" to this.toString())
    return ObjectMapper().writeValueAsString(jsonObject)
  }
}
