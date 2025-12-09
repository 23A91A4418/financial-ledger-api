package com.ledger.service;

import com.ledger.exception.InsufficientFundsException;
import com.ledger.exception.InvalidRequestException;
import com.ledger.exception.ResourceNotFoundException;

import com.ledger.model.Account;
import com.ledger.model.LedgerEntry;
import com.ledger.model.Transaction;

import com.ledger.repository.AccountRepository;
import com.ledger.repository.LedgerEntryRepository;
import com.ledger.repository.TransactionRepository;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final LedgerEntryRepository ledgerEntryRepository;

    public TransactionService(TransactionRepository transactionRepository,
                              AccountRepository accountRepository,
                              LedgerEntryRepository ledgerEntryRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.ledgerEntryRepository = ledgerEntryRepository;
    }

    // ----------------------------------------------------
    // CREATE SIMPLE CREDIT/DEBIT TRANSACTION
    // ----------------------------------------------------
    public Transaction createTransaction(Transaction transaction) {

        Account account = accountRepository.findById(transaction.getAccountId())
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));

        if ("credit".equalsIgnoreCase(transaction.getType())) {
            account.setBalance(account.getBalance() + transaction.getAmount());
        } else if ("debit".equalsIgnoreCase(transaction.getType())) {

            double currentBalance = getBalance(transaction.getAccountId());
            if (currentBalance < transaction.getAmount()) {
                throw new InsufficientFundsException("Insufficient funds for debit");
            }

            account.setBalance(account.getBalance() - transaction.getAmount());
        } else {
            throw new InvalidRequestException("Invalid transaction type");
        }

        accountRepository.save(account);

        Transaction saved = transactionRepository.save(transaction);

        LedgerEntry entry = new LedgerEntry();
        entry.setTransactionId(saved.getId());
        entry.setAccountId(account.getId());

        if ("credit".equalsIgnoreCase(transaction.getType())) {
            entry.setCredit(transaction.getAmount());
            entry.setDebit(0.0);
        } else {
            entry.setDebit(transaction.getAmount());
            entry.setCredit(0.0);
        }

        ledgerEntryRepository.save(entry);

        return saved;
    }

    // ----------------------------------------------------
    // TRANSFER BETWEEN TWO ACCOUNTS
    // ----------------------------------------------------
    public void transfer(Long fromAccountId, Long toAccountId, Double amount) {

        if (amount == null || amount <= 0) {
            throw new InvalidRequestException("Amount must be greater than zero");
        }

        Account sender = accountRepository.findById(fromAccountId)
                .orElseThrow(() -> new ResourceNotFoundException("Sender account not found"));

        Account receiver = accountRepository.findById(toAccountId)
                .orElseThrow(() -> new ResourceNotFoundException("Receiver account not found"));

        double senderBalance = getBalance(fromAccountId);
        if (senderBalance < amount) {
            throw new InsufficientFundsException("Sender has insufficient funds");
        }

        // DEBIT sender
        sender.setBalance(senderBalance - amount);
        accountRepository.save(sender);

        LedgerEntry senderEntry = new LedgerEntry();
        senderEntry.setAccountId(fromAccountId);
        senderEntry.setDebit(amount);
        senderEntry.setCredit(0.0);
        ledgerEntryRepository.save(senderEntry);

        // CREDIT receiver
        receiver.setBalance(receiver.getBalance() + amount);
        accountRepository.save(receiver);

        LedgerEntry receiverEntry = new LedgerEntry();
        receiverEntry.setAccountId(toAccountId);
        receiverEntry.setCredit(amount);
        receiverEntry.setDebit(0.0);
        ledgerEntryRepository.save(receiverEntry);
    }

    // ----------------------------------------------------
    // CALCULATE BALANCE USING LEDGER
    // ----------------------------------------------------
    public double getBalance(Long accountId) {
        List<LedgerEntry> entries = ledgerEntryRepository.findByAccountId(accountId);

        double totalCredits = entries.stream().mapToDouble(LedgerEntry::getCredit).sum();
        double totalDebits = entries.stream().mapToDouble(LedgerEntry::getDebit).sum();

        return totalCredits - totalDebits;
    }

    // ----------------------------------------------------
    // GET ALL TRANSACTIONS
    // ----------------------------------------------------
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }
}
