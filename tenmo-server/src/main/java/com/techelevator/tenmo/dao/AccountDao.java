package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;

import java.security.Principal;

public interface AccountDao {

    Account getBalance(int accountId);

}
