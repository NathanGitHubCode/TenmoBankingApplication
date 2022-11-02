package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.model.Account;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@RestController
@RequestMapping("http://localhost:8080/")
public class AccountController {

    private JdbcAccountDao jdbcAccountDao;
    private JdbcUserDao jdbcUserDao;


    public AccountController(JdbcAccountDao accountDao, JdbcUserDao userDao){
       this.jdbcAccountDao = accountDao;
       this.jdbcUserDao = userDao;
   }

   @RequestMapping (path = "balance/{id}", method = RequestMethod.GET)
    public Account getBalance(@PathVariable int accountId){
        Account account = jdbcAccountDao.getBalance(accountId);
       if (account == null) {
           throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account balance Not Found");
       } else {
           return account;
       }
   }



}
