package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;

import javax.security.auth.login.AccountNotFoundException;
import java.math.BigDecimal;
import java.security.Principal;

public interface AccountDao {

    BigDecimal getBalance(int userId);
    int findAccountIdByUsername(String username);
    void updateBalance(BigDecimal balance, int account);

}
