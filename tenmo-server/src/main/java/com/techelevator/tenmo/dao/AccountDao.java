package com.techelevator.tenmo.dao;

import java.math.BigDecimal;

public interface AccountDao {

    BigDecimal getBalance(int userId);
    int findAccountIdByUsername(String username);
    boolean updateBalance(BigDecimal balance, int account);

}
