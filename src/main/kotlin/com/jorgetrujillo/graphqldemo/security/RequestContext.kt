package com.jorgetrujillo.graphqldemo.security

import org.springframework.context.annotation.Scope
import org.springframework.context.annotation.ScopedProxyMode
import org.springframework.stereotype.Component

@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
@Component
class RequestContext {

  val userGroups: MutableList<String> = mutableListOf()

  fun addGroups(groups: List<String>) {
    userGroups.addAll(groups)
  }

  fun getGroups(): List<String> {
    return userGroups
  }
}
