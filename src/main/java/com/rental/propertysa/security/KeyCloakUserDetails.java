package com.rental.propertysa.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class KeyCloakUserDetails implements UserDetails {
    private final String username;
    private final String role;
    private final String token;

    public KeyCloakUserDetails(String token) {
        this.token = token;
        DecodedJWT decodedJWT = JWT.decode(token);
        username = decodedJWT.getClaim("email").asString();
        List<String> roles = new ArrayList<>();
        if (!decodedJWT.getClaim("roles").isMissing()) {
            roles = decodedJWT.getClaim("roles").asList(String.class);
        } else if (!decodedJWT.getClaim("realm_access").isMissing() &&
                decodedJWT.getClaim("realm_access").asMap().get("roles") != null) {
            roles = (List<String>) decodedJWT.getClaim("realm_access").asMap().get("roles");
        }

        System.out.println(roles);
        if (roles != null && roles.contains("ADMIN")) {
            role = "ADMIN";
        } else if (roles != null && roles.contains("OWNER")) {
            role = "OWNER";
        } else {
            role = "CUSTOMER";
        }
        System.out.println(role);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        HashSet<GrantedAuthority> simpleGrantedAuthorities = new HashSet<>();
        simpleGrantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        return simpleGrantedAuthorities;
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
