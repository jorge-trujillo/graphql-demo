package com.jorgetrujillo.graphqlclient.domain

data class GraphQLResponse<T>(
  var statusCode: Int? = null,
  val data: Map<String, T>?
) {

  constructor(data: Map<String, T>): this(null, data)
}
