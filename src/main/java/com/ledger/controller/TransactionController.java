package com.ledger.controller;

import com.ledger.dto.TransferRequest;
import com.ledger.model.Transaction;
import com.ledger.service.TransactionService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    // Create a normal credit or debit transaction
    @PostMapping
    public Transaction createTransaction(@RequestBody Transaction transaction) {
        return transactionService.createTransaction(transaction);
    }

    // Transfer endpoint (POST JSON body)
   @PostMapping("/transfer")
public String transfer(@RequestBody TransferRequest request) {
    transactionService.transfer(
            request.getFromAccountId(),
            request.getToAccountId(),
            request.getAmount()
    );
    return "Transfer successful";
}


    // Get all transactions
    @GetMapping
    public List<Transaction> getAllTransactions() {
        return transactionService.getAllTransactions();
    }
}
