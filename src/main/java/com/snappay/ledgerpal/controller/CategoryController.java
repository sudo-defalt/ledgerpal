package com.snappay.ledgerpal.controller;

import com.snappay.ledgerpal.model.CategoryModel;
import com.snappay.ledgerpal.model.operation.CreateCategoryModel;
import com.snappay.ledgerpal.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    public CategoryModel create(@AuthenticationPrincipal UserDetails userDetails,
                                @RequestBody CreateCategoryModel model) {
        return categoryService.create(userDetails.getUsername(), model);
    }

    @GetMapping
    public Page<CategoryModel> getAll(@AuthenticationPrincipal UserDetails userDetails, Pageable pageable) {
        return categoryService.getAll(userDetails.getUsername(), pageable);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<?> getOne(@AuthenticationPrincipal UserDetails userDetails, @PathVariable UUID uuid) {
        return categoryService.getOne(userDetails.getUsername(), uuid)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
