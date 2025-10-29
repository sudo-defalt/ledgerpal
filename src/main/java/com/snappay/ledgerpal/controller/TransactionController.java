package com.snappay.ledgerpal.controller;

import com.snappay.ledgerpal.model.TransactionModel;
import com.snappay.ledgerpal.model.operation.CreateTransactionModel;
import com.snappay.ledgerpal.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    @GetMapping
    public PagedModel<TransactionModel> search(@AuthenticationPrincipal UserDetails userDetails,
                                         @RequestParam Map<String, String> filters, Pageable pageable) {
        return new PagedModel<>(transactionService.search(userDetails.getUsername(), filters, pageable));
    }

    @PostMapping
    public TransactionModel create(@AuthenticationPrincipal UserDetails userDetails, @RequestBody CreateTransactionModel model) {
        return transactionService.create(userDetails.getUsername(), model);
    }
}
