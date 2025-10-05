package com.snappay.ledgerpal.security;

import com.snappay.ledgerpal.model.UserModel;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class ConcreteUserDetails implements UserDetails {
    private final String username;
    private final String password;
    private final List<ConcreteGrantedAuthority> authorities;

    public ConcreteUserDetails(UserModel user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.authorities = user.getAuthorities().stream()
                .map(ConcreteGrantedAuthority::new).toList();
    }

    @Override
    public Collection<ConcreteGrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }
}
