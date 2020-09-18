package com.jorgetrujillo.graphqldemo

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.jorgetrujillo.graphqlclient.client.GraphQLClient
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification
import spock.lang.Stepwise

import java.net.http.HttpClient

@SpringBootTest(
    classes = [GraphqlDemoApplication],
    webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
)
@ContextConfiguration(classes = [GraphqlDemoApplication])
@Stepwise
@Slf4j
class TestBase extends Specification {

  @Value('${server.port:8080}')
  int port

  @Value('${mock_server.host}')
  String mockServerHost

  @Autowired
  MongoTemplate mongoTemplate

  static final int DEFAULT_PORT = 1080
  static String urlBase = 'http://localhost:9000/graphql'

  GraphQLClient graphQLClient

  void setup() {
    graphQLClient = new GraphQLClient(
        urlBase,
        new ObjectMapper().registerModule(new KotlinModule()),
        HttpClient.newHttpClient()
    )
    graphQLClient.addGlobalHeader('SM_USER', 'user')
    graphQLClient.addGlobalHeader('X-Groups', 'ADMIN')
  }

}
