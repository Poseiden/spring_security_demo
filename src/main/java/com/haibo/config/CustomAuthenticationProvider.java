package com.haibo.config;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class CustomAuthenticationProvider implements AuthenticationProvider{

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        PreAuthenticatedAuthenticationToken preAuthenticatedAuthenticationToken = (PreAuthenticatedAuthenticationToken) authentication;
        UserPrivilege userPrivilege = (UserPrivilege) preAuthenticatedAuthenticationToken.getPrincipal();

        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(new CustomAuthentication(userPrivilege));
        return securityContext.getAuthentication();
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (PreAuthenticatedAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
