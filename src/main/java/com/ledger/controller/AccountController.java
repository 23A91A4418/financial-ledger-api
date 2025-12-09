package com.ledger.controller;

import com.ledger.model.Account;

import com.ledger.service.AccountService;
import com.ledger.service.TransactionService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;
    private final TransactionService transactionService;

    public AccountController(AccountService accountService, TransactionService transactionService) {
        this.accountService = accountService;
        this.transactionService = transactionService;
    }

    @PostMapping
    public Account createAccount(@RequestBody Account account) {
        return accountService.createAccount(account);
    }

    @GetMapping
    public List<Account> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    // ================================
    // GET SINGLE ACCOUNT BALANCE
    // ================================
    @GetMapping("/{id}/balance")
    public double getBalance(@PathVariable Long id) {
        return transactionService.getBalance(id);
    }

    
}
