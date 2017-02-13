package com.haibo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
public class CustomAuthenticationFilter extends AbstractPreAuthenticatedProcessingFilter{

    @Autowired
    public CustomAuthenticationFilter(@Qualifier("authenticationManager")AuthenticationManager authenticationManager) {
        setAuthenticationManager(authenticationManager);
    }

    @Override
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
        String token = request.getHeader(AUTHORIZATION);
        UserPrivilege userPrivilege = new UserPrivilege();
        userPrivilege.setName("test");

        if (Objects.isNull(token)) {
            return null;
        } else if ("admin".equals(token)) {
            List<String> list = new ArrayList<String>() {{add("ROLE_ADMIN");}};
            userPrivilege.setPrivilegeCodes(list);
        } else if ("user".equals(token)) {
            List<String> list = new ArrayList<String>() {{add("ROLE_USER");}};
            userPrivilege.setPrivilegeCodes(list);
        }

        return userPrivilege;
    }

    @Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
        return null;
    }
}
