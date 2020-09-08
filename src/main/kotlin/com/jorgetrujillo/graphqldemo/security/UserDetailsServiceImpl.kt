package com.jorgetrujillo.graphqldemo.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component

@Component
class UserDetailsServiceImpl(
    val requestContext: RequestContext
) : UserDetailsService {

  /**
   * Load all user details from the appropriate source.
   * In this example, use blessed headers to get group info
   */
  override fun loadUserByUsername(username: String): UserDetails {
    //NOTE The implementation of this method is for example purposes only.
    val authorities: MutableList<GrantedAuthority> = mutableListOf()
    //val authority: SimpleGrantedAuthority = SimpleGrantedAuthority("ADMIN")

    requestContext.userGroups.forEach {
      authorities.add(SimpleGrantedAuthority(it.toUpperCase()))
    }

    //TODO Why do we need a password here if we are pre-authenticating?
    val user: UserDetails = User(
        authorities = authorities,
        enabled = true,
        userName = username,
        password = "NONE"
    )

    return user
  }

}
