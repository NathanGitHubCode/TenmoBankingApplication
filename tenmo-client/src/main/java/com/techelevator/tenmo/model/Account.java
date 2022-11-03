package com.techelevator.tenmo.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Account {

    private int accountId;
    private int userId;
    private BigDecimal balance;

    public int getAccountId() {
        return accountId;
    }

    public int getUserId() {
        return userId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return accountId == account.accountId && userId == account.userId && Objects.equals(balance, account.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, userId, balance);
    }
}
