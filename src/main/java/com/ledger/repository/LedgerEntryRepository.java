package com.ledger.repository;

import com.ledger.model.LedgerEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LedgerEntryRepository extends JpaRepository<LedgerEntry, Long> {

    // Fetch ALL ledger records for an account
    List<LedgerEntry> findByAccountId(Long accountId);
}
