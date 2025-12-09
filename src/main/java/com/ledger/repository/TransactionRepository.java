package com.ledger.repository;

import com.ledger.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    // Get all transactions for one account
    List<Transaction> findByAccountIdOrderByTimestampDesc(Long accountId);
}
