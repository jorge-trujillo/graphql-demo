package com.jorgetrujillo.graphqldemo.config

import graphql.language.StringValue
import graphql.schema.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.Instant
import java.time.format.DateTimeParseException

@Configuration
class GraphQLDateConfig {

  val coercing = object : Coercing<Instant, String> {

    override fun serialize(dataFetcherResult: Any): String {
      if (dataFetcherResult is Instant) {
        return dataFetcherResult.toString()
      } else {
        throw CoercingSerializeException("Expected a LocalDate object.")
      }
    }

    override fun parseValue(input: Any): Instant {
      try {
        if (input is String) {
          return Instant.parse(input)
        } else {
          throw CoercingParseValueException("Expected a String")
        }
      } catch (e: DateTimeParseException) {
        throw CoercingParseValueException(String.format("Not a valid date: '%s'.", input), e
        )
      }
    }

    override fun parseLiteral(input: Any): Instant {
      if (input is StringValue) {
        try {
          return Instant.parse(input.toString())
        } catch (e: DateTimeParseException) {
          throw CoercingParseLiteralException(e)
        }
      } else {
        throw CoercingParseLiteralException("Expected a StringValue.")
      }
    }
  }

  @Bean
  fun dateScalar(): GraphQLScalarType {
    return GraphQLScalarType.newScalar()
        .name("Date")
        .description("Java Instant as scalar.")
        .coercing(coercing).build()
  }
}