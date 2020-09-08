package com.jorgetrujillo.graphqldemo.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.UserDetailsByNameServiceWrapper
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken
import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter
import org.springframework.web.context.request.RequestContextListener


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
//@EnableConfigurationProperties(SecurityProperties::class)
class WebSecurityConfig : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        http
                .addFilterBefore(ssoFilter(), RequestHeaderAuthenticationFilter::class.java)
                .authenticationProvider(preauthAuthProvider())
                .csrf().disable()
                .authorizeRequests().anyRequest().authenticated()
    }

    @Bean
    fun ssoFilter(): SSORequestHeaderAuthenticationFilter? {
        val filter = SSORequestHeaderAuthenticationFilter()
        filter.setAuthenticationManager(authenticationManager())
        return filter
    }

    @Bean
    fun requestContextListener(): RequestContextListener? {
        return RequestContextListener()
    }

    @Autowired
    fun configureGlobal(auth: AuthenticationManagerBuilder) {
        auth.authenticationProvider(preauthAuthProvider())
    }

    @Bean
    fun userDetailsServiceWrapper(): UserDetailsByNameServiceWrapper<PreAuthenticatedAuthenticationToken>? {
        val wrapper = UserDetailsByNameServiceWrapper<PreAuthenticatedAuthenticationToken>()
        wrapper.setUserDetailsService(CustomUserDetailsService())
        return wrapper
    }

    @Bean
    fun preauthAuthProvider(): PreAuthenticatedAuthenticationProvider? {
        val preauthAuthProvider = PreAuthenticatedAuthenticationProvider()
        preauthAuthProvider.setPreAuthenticatedUserDetailsService(userDetailsServiceWrapper())
        return preauthAuthProvider
    }

}