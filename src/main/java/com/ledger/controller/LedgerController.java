package com.ledger.controller;

import com.ledger.model.LedgerEntry;
import com.ledger.repository.LedgerEntryRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ledger")
public class LedgerController {

    private final LedgerEntryRepository ledgerEntryRepository;

    public LedgerController(LedgerEntryRepository ledgerEntryRepository) {
        this.ledgerEntryRepository = ledgerEntryRepository;
    }

    @GetMapping
    public List<LedgerEntry> getAllEntries() {
        return ledgerEntryRepository.findAll();
    }
}
