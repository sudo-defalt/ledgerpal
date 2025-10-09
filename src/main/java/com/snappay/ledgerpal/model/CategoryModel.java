package com.snappay.ledgerpal.model;

import com.snappay.ledgerpal.entity.Category;
import lombok.Data;

import java.util.UUID;

@Data
public class CategoryModel {
    private UUID uuid;
    private String name;
    private UUID user;

    public static CategoryModel build(Category category) {
        CategoryModel model = new CategoryModel();
        model.setUuid(category.getUuid());
        model.setName(category.getName());
        model.setUser(category.getUser().getUuid());
        return model;
    }
}
