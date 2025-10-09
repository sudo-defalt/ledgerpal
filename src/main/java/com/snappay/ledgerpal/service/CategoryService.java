package com.snappay.ledgerpal.service;

import com.snappay.ledgerpal.entity.Category;
import com.snappay.ledgerpal.entity.User;
import com.snappay.ledgerpal.exception.UserNotFoundException;
import com.snappay.ledgerpal.model.CategoryModel;
import com.snappay.ledgerpal.model.operation.CreateCategoryModel;
import com.snappay.ledgerpal.repository.CategoryRepository;
import com.snappay.ledgerpal.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    @Transactional
    public CategoryModel create(String username, CreateCategoryModel model) {
        User user = userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
        Category category = Category.builder().uuid(UUID.randomUUID()).user(user).name(model.getName()).build();
        categoryRepository.save(category);
        return CategoryModel.build(category);
    }
}
