package com.techelevator.tenmo.model;

import java.util.Objects;

public class Account {

    private int account_id;
    private int user_id;
    private double balance;

    public int getAccount_id() {
        return account_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public double getBalance() {
        return balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return account_id == account.account_id && user_id == account.user_id && Double.compare(account.balance, balance) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(account_id, user_id, balance);
    }
}
