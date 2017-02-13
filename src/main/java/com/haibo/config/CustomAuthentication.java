package com.haibo.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.stream.Collectors;

public class CustomAuthentication implements Authentication{

    private UserPrivilege userPrivilege;

    public CustomAuthentication(UserPrivilege userPrivilege) {
        this.userPrivilege = userPrivilege;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.userPrivilege.getPrivilegeCodes().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.userPrivilege;
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

    }

    @Override
    public String getName() {
        return null;
    }
}
