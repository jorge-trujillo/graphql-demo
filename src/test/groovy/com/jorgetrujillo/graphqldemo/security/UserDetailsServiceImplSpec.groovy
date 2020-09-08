package com.jorgetrujillo.graphqldemo.security

import org.springframework.security.core.userdetails.UserDetails
import spock.lang.Specification

class UserDetailsServiceImplSpec extends Specification {

  UserDetailsServiceImpl userDetailsService

  void setup() {
    userDetailsService = new UserDetailsServiceImpl(
        Mock(RequestContext)
    )
  }

  void 'Load user details'() {

    given:
    String userName = 'user1'
    List<String> userGroups = ['group1', 'group2']

    when:
    UserDetails userDetails = userDetailsService.loadUserByUsername(userName)

    then:
    1 * userDetailsService.requestContext.userGroups >> userGroups
    0 * _

    userDetails.username == userName
    userDetails.enabled
    userDetails.authorities.size() == 2
    userDetails.authorities.find { it.authority == 'GROUP1' }
    userDetails.authorities.find { it.authority == 'GROUP2' }

  }
}
