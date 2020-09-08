package com.jorgetrujillo.graphqldemo.config

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

data class User(
        internal val authorities: Collection<GrantedAuthority>,
        internal val enabled: Boolean,
        internal val userName: String,
        internal val password: String,
        internal val credentialsNonExpired: Boolean = true,
        internal val accountNonExpired: Boolean = true,
        internal val accountNonLocked: Boolean = true

) : UserDetails {

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return authorities
    }

    override fun isEnabled(): Boolean {
        return enabled
    }

    override fun getUsername(): String {
        return userName
    }

    override fun getPassword(): String {
        return password
    }

    override fun isCredentialsNonExpired(): Boolean {
        return credentialsNonExpired
    }

    override fun isAccountNonExpired(): Boolean {
        return accountNonExpired
    }

    override fun isAccountNonLocked(): Boolean {
        return accountNonLocked
    }
}
