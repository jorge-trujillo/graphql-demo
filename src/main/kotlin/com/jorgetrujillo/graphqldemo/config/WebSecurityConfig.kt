package com.jorgetrujillo.graphqldemo.config

import com.jorgetrujillo.graphqldemo.security.UserDetailsServiceImpl
import com.jorgetrujillo.graphqldemo.security.RequestContext
import com.jorgetrujillo.graphqldemo.security.RequestHeaderAuthenticationFilterImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsByNameServiceWrapper
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class WebSecurityConfig(
    val customUserDetailsService: UserDetailsServiceImpl,
    val requestContext: RequestContext
) : WebSecurityConfigurerAdapter() {

  override fun configure(http: HttpSecurity) {
    http
        .addFilterBefore(ssoFilter(), RequestHeaderAuthenticationFilterImpl::class.java)
        .authenticationProvider(preauthAuthProvider())
        .csrf().disable()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
  }

  @Bean
  fun ssoFilter(): RequestHeaderAuthenticationFilterImpl? {
    val requestAuthFilter = RequestHeaderAuthenticationFilterImpl(requestContext)
    requestAuthFilter.setAuthenticationManager(authenticationManager())
    return requestAuthFilter
  }

  @Autowired
  fun configureGlobal(auth: AuthenticationManagerBuilder) {
    auth.authenticationProvider(preauthAuthProvider())
  }

  @Bean
  fun userDetailsServiceWrapper(): UserDetailsByNameServiceWrapper<PreAuthenticatedAuthenticationToken>? {

    val wrapper = UserDetailsByNameServiceWrapper<PreAuthenticatedAuthenticationToken>()
    wrapper.setUserDetailsService(customUserDetailsService)
    return wrapper
  }

  @Bean
  fun preauthAuthProvider(): PreAuthenticatedAuthenticationProvider? {
    val preauthAuthProvider = PreAuthenticatedAuthenticationProvider()
    preauthAuthProvider.setPreAuthenticatedUserDetailsService(userDetailsServiceWrapper())
    return preauthAuthProvider
  }

}
