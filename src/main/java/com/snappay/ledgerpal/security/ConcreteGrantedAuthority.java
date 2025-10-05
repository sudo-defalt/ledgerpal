package com.snappay.ledgerpal.security;

import com.snappay.ledgerpal.model.AuthorityModel;
import org.springframework.security.core.GrantedAuthority;

public class ConcreteGrantedAuthority implements GrantedAuthority {
    private final String authority;

    public ConcreteGrantedAuthority(AuthorityModel authority) {
        this.authority = "ROLE_" + authority.getName();
    }

    @Override
    public String getAuthority() {
        return authority;
    }
}
