package com.jorgetrujillo.graphqldemo.security

import spock.lang.Specification
import spock.lang.Unroll

import javax.servlet.http.HttpServletRequest

class RequestHeaderAuthenticationFilterImplSpec extends Specification {

  RequestHeaderAuthenticationFilterImpl requestHeaderAuthenticationFilter

  void setup() {
    requestHeaderAuthenticationFilter = new RequestHeaderAuthenticationFilterImpl(
        Mock(RequestContext)
    )
  }

  @Unroll
  void 'Requests get headers for username and groups'() {

    given:
    HttpServletRequest servletRequest = Mock(HttpServletRequest)
    String expectedUserName = 'user1'

    when:
    String userName = requestHeaderAuthenticationFilter.getPreAuthenticatedPrincipal(servletRequest)

    then:
    1 * servletRequest.getHeader(RequestHeaderAuthenticationFilterImpl.USER_ID_HEADER) >> expectedUserName
    1 * servletRequest.getHeader(RequestHeaderAuthenticationFilterImpl.GROUPS_HEADER) >> groupString
    1 * requestHeaderAuthenticationFilter.requestContext.addGroups(expectedGroups)
    0 * _

    userName == expectedUserName

    where:
    groupString     | expectedGroups
    ''              | []
    null            | []
    'group1,group2' | ['group1', 'group2']
  }

  void 'Requests with no user header return null'() {

    given:
    HttpServletRequest servletRequest = Mock(HttpServletRequest)

    when:
    String userName = requestHeaderAuthenticationFilter.getPreAuthenticatedPrincipal(servletRequest)

    then:
    1 * servletRequest.getHeader(RequestHeaderAuthenticationFilterImpl.USER_ID_HEADER) >> null
    0 * _

    !userName
  }

}
