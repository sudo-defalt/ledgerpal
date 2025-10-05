package com.snappay.ledgerpal.model;

import com.snappay.ledgerpal.entity.User;
import lombok.Data;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
public class UserModel {
    private UUID uuid;
    private String username;
    private String password;
    private String email;
    private Set<AuthorityModel> authorities;

    public static UserModel build(User user) {
        UserModel model = new UserModel();
        model.setUuid(user.getUuid());
        model.setUsername(user.getUsername());
        model.setUsername(user.getPassword());
        model.setEmail(user.getEmail());
        model.setAuthorities(user.getAuthorities().stream()
                .map(AuthorityModel::build)
                .collect(Collectors.toSet()));
        return model;
    }
}
