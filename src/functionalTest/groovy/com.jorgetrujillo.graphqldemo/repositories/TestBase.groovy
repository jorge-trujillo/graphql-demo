package com.jorgetrujillo.graphqldemo.repositories

import com.jorgetrujillo.graphqldemo.GraphqlDemoApplication
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification
import spock.lang.Stepwise

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
  static String urlBase

  void setup() {
  }

}
