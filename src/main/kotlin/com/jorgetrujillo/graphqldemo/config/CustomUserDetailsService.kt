package com.jorgetrujillo.graphqldemo.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Scope
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import org.springframework.web.context.WebApplicationContext.SCOPE_REQUEST
import javax.servlet.http.HttpServletRequest

@Scope(SCOPE_REQUEST)
@Component
class CustomUserDetailsService() : UserDetailsService {

    @Autowired
    lateinit var request: HttpServletRequest

    companion object {
        val GROUPS_HEADER = "X-groups"

    }

    override fun loadUserByUsername(username: String): UserDetails {
        //NOTE The implementation of this method is for example purposes only.
        val authorities: MutableList<GrantedAuthority> = mutableListOf()

        val groups = request.getHeader(GROUPS_HEADER)
        val groupList = groups.split(",")

        //val authority: SimpleGrantedAuthority = SimpleGrantedAuthority("ADMIN")
        groupList.forEach {
            authorities.add(SimpleGrantedAuthority(it.toUpperCase()))
        }

        //TODO Why do we need a password here if we are pre-authenticating?
        val user: UserDetails = User(
                authorities = authorities,
                enabled = true,
                userName = username,
                password = "NONE")

        return user
    }

}