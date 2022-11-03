package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.*;
import com.techelevator.tenmo.model.Account;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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



}
