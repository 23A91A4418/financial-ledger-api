package com.ledger.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "ledger_entries")
public class LedgerEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long transactionId;
    private Long accountId;

    private Double credit = 0.0;
    private Double debit = 0.0;
}
