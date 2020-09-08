package com.jorgetrujillo.graphqldemo.config

import org.springframework.context.annotation.Scope
import org.springframework.context.annotation.ScopedProxyMode
import org.springframework.stereotype.Component

@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
@Component
class RequestContext {

    var requestHeaders : MutableMap<String, List<String>> = mutableMapOf()

    fun addHeader(headerName : String, headerValues : List<String>) {
        requestHeaders[headerName] = headerValues
    }

    fun getHeader(headerName : String) : List<String>? {
        return requestHeaders[headerName]
    }
}