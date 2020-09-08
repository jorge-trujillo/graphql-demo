package com.jorgetrujillo.graphqldemo.exceptions

import org.springframework.http.HttpStatus
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider
import org.springframework.security.web.authentication.preauth.PreAuthenticatedCredentialsNotFoundException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.io.IOException
import javax.servlet.http.HttpServletResponse


@ControllerAdvice
class RestExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(PreAuthenticatedCredentialsNotFoundException::class)
    fun handlePreAuthenticatedCredentialsNotFound(response: HttpServletResponse) {
        response.sendError(HttpStatus.UNAUTHORIZED.value())
    }
}