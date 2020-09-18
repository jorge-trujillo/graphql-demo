package com.jorgetrujillo.graphqlclient.domain

data class GraphQLField(
  val name: String,
  val fields: List<GraphQLField>? = null
) {

  override fun toString(): String {
    var body = name
    if (fields != null && fields.isNotEmpty()) {
      body += """ {
        ${fields.map { it.toString() }.joinToString(separator = "\n")}
      }"""
    }

    return body
  }
}
