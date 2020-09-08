package com.jorgetrujillo.graphqldemo.security

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.web.authentication.preauth.PreAuthenticatedCredentialsNotFoundException
import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter
import javax.servlet.http.HttpServletRequest

class RequestHeaderAuthenticationFilterImpl(
    private val requestContext: RequestContext
) : RequestHeaderAuthenticationFilter() {

  companion object {
    const val USER_ID_HEADER = "SM_USER"
    const val GROUPS_HEADER = "X-Groups"

    val log: Logger = LoggerFactory.getLogger(this::class.java)
  }

  private val allowPreAuthenticatedPrincipals = true

  init {
    this.setPrincipalRequestHeader(USER_ID_HEADER)
  }

  /**
   * This is called when a request is made, the returned object identifies the
   * user and will either be null or a String. This method will throw an exception if
   * exceptionIfHeaderMissing is set to true (default) and the required header is missing.
   *
   * @param request [javax.servlet.http.HttpServletRequest]
   */
  override fun getPreAuthenticatedPrincipal(request: HttpServletRequest?): Any? {
    try {
      val userName = super.getPreAuthenticatedPrincipal(request) as String?

      if (request != null) {
        // Set groups
        val groups = request.getHeader(GROUPS_HEADER)?.split(",")?.filter { it.isNotEmpty() } ?: listOf()
        requestContext.addGroups(groups)
      }

      return userName
    } catch (e: PreAuthenticatedCredentialsNotFoundException) {
      log.warn("No $USER_ID_HEADER header found for request")
    }

    return null
  }

  fun isAllowPreAuthenticatedPrincipals(): Boolean {
    return allowPreAuthenticatedPrincipals
  }
}
