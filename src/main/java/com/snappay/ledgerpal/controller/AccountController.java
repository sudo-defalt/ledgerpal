package com.snappay.ledgerpal.controller;

import com.snappay.ledgerpal.model.AccountModel;
import com.snappay.ledgerpal.model.operation.CreateAccountModel;
import com.snappay.ledgerpal.service.AccountService;
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
@RequestMapping("/accounts")
public class AccountController {
    private final AccountService accountService;

    @PostMapping
    public AccountModel create(@AuthenticationPrincipal UserDetails userDetails,
                               @RequestBody CreateAccountModel model) {
        return accountService.create(userDetails.getUsername(), model);
    }

    @GetMapping
    public Page<AccountModel> getOne(@AuthenticationPrincipal UserDetails userDetails, Pageable pageable) {
        return accountService.getAll(userDetails.getUsername(), pageable);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<?> getOne(@AuthenticationPrincipal UserDetails userDetails, @PathVariable UUID uuid) {
        return accountService.getOne(userDetails.getUsername(), uuid)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
