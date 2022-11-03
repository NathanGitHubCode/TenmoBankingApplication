package com.techelevator.tenmo.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.model.Account;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.security.auth.login.AccountNotFoundException;
import javax.sql.DataSource;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.security.Principal;

@RestController
public class AccountController {

    private final AccountDao accountDao;


    public AccountController(AccountDao accountDao){
         this.accountDao = accountDao;
   }

   @RequestMapping (path = "/account/{id}", method = RequestMethod.GET)
    public Account getBalance(@PathVariable("id") int accountId) {
        Account account = accountDao.getBalance(accountId);
       if (account == null) {
           throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account balance Not Found");
       } else {
           return account;
       }
   }



}
