package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.*;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AccountDTO;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.security.Principal;

@RestController
public class AccountController {

    private final AccountDao accountDao;
    private final UserDao userDao;

    public AccountController(UserDao userDao, AccountDao accountDao){
        this.userDao = userDao;
        this.accountDao = accountDao;
   }

   @RequestMapping (path = "/account", method = RequestMethod.GET)
    public BigDecimal getBalance(Principal principal) {
        String username = principal.getName();
        int userId = userDao.findIdByUsername(username);
        BigDecimal accountBalance = accountDao.getBalance(userId);
       if (accountBalance == null) {
           throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account balance Not Found");
       } else {
           return accountBalance;
       }
   }

   @RequestMapping (path = "/account/balance", method = RequestMethod.PUT)
    public void updateBalance(@Valid @RequestBody AccountDTO transfer) {

       int accountFrom = transfer.getAccountFrom();
       int accountTo = transfer.getAccountTo();
       if(accountFrom != accountTo) {
           BigDecimal transferAmount = transfer.getAmount();
           BigDecimal fromBalance = accountDao.getBalance(accountFrom).subtract(transferAmount);
           BigDecimal toBalance = accountDao.getBalance(accountTo).add(transferAmount);
           accountDao.updateBalance(fromBalance, accountFrom);
           accountDao.updateBalance(toBalance, accountTo);
       }
       else {
           throw new ResourceAccessException("You can't send money to yourself");
       }
   }



}
