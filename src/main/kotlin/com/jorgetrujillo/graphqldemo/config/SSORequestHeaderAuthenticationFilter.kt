package com.jorgetrujillo.graphqldemo.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter
import javax.servlet.http.HttpServletRequest

class SSORequestHeaderAuthenticationFilter() : RequestHeaderAuthenticationFilter() {

    @Autowired
    lateinit var requestContext: RequestContext

    private val allowPreAuthenticatedPrincipals = true

    init {
        this.setPrincipalRequestHeader("SM_USER")
    }

    /**
     * This is called when a request is made, the returned object identifies the
     * user and will either be null or a String. This method will throw an exception if
     * exceptionIfHeaderMissing is set to true (default) and the required header is missing.
     *
     * @param request [javax.servlet.http.HttpServletRequest]
     */
    override fun getPreAuthenticatedPrincipal(request: HttpServletRequest?): Any? {
        val userName = super.getPreAuthenticatedPrincipal(request) as String?

        if (request != null) {
            // Set headers
            request.headerNames.asIterator().forEach {
                requestContext.addHeader(it, request.getHeaders(it).toList())
            }
        }

        return userName
    }

    fun isAllowPreAuthenticatedPrincipals(): Boolean {
        return allowPreAuthenticatedPrincipals
    }
}
