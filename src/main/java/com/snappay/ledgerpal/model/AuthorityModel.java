package com.snappay.ledgerpal.model;

import com.snappay.ledgerpal.entity.Authority;
import lombok.Data;

@Data
public class AuthorityModel {
    private String name;
    private String description;

    public static AuthorityModel build(Authority authority) {
        AuthorityModel model = new AuthorityModel();
        model.setName(authority.getName());
        model.setDescription(authority.getDescription());
        return model;
    }
}
