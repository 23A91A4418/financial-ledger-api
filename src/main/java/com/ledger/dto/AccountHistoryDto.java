package com.ledger.dto;

import java.util.List;

public class AccountHistoryDto {
    private Long accountId;
    private Double currentBalance;
    private List<TransactionDto> transactions;

    // getters / setters
    public Long getAccountId() { return accountId; }
    public void setAccountId(Long accountId) { this.accountId = accountId; }
    public Double getCurrentBalance() { return currentBalance; }
    public void setCurrentBalance(Double currentBalance) { this.currentBalance = currentBalance; }
    public List<TransactionDto> getTransactions() { return transactions; }
    public void setTransactions(List<TransactionDto> transactions) { this.transactions = transactions; }
}
