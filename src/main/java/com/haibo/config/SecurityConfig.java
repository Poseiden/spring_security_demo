package com.haibo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.savedrequest.NullRequestCache;

@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomAuthenticationProvider customAuthenticationProvider;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception{
        authenticationManagerBuilder.authenticationProvider(customAuthenticationProvider);
    }

    @Configuration
    public static class CustomWebSecurityConfigureAdapter extends WebSecurityConfigurerAdapter {
        @Autowired
        private AbstractPreAuthenticatedProcessingFilter abstractPreAuthenticatedProcessingFilter;

        @Override
        protected void configure(HttpSecurity httpSecurity) throws Exception {
            httpSecurity.sessionManagement().disable();
            httpSecurity.securityContext().disable();
            httpSecurity.requestCache().requestCache(new NullRequestCache());
            httpSecurity.addFilter(abstractPreAuthenticatedProcessingFilter);
            httpSecurity
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/swagger-ui.html", "/webjars/springfox-swagger-ui/**",
                        "/swagger-resources/**", "/swagger/**").permitAll()
                .antMatchers("/hystrix/**", "/hystrix.stream", "/proxy.stream").permitAll()
                .anyRequest().authenticated();
        }
    }
}
