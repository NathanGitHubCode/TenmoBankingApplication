package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.security.Principal;

@RestController
public class TransferController {

    private final AccountDao accountDao;
    private final TransferDao transferDao;
    private final UserDao userDao;

    public TransferController(TransferDao transferDao, AccountDao accountDao, UserDao userDao){
        this.userDao = userDao;
        this.accountDao = accountDao;
        this.transferDao = transferDao;

    }

    @RequestMapping(path = "/transfer", method = RequestMethod.POST)
    public void sendTransfer(@Valid @RequestBody Transfer transfer, Principal principal) {
        String usernameFrom = principal.getName();
        //String usernameTo = userDao.findByUsername();
        int principalAccountId = accountDao.findAccountIdByUsername(usernameFrom);
        int accountFrom = transfer.getAccountFrom();
//        int accountTo = accountDao.findAccountIdByUsername(usernameTo);
        int accountTo = transfer.getAccountTo();
        if(accountTo != accountFrom){
            BigDecimal transferAmount = transfer.getAmount();
            BigDecimal sendingBalance = accountDao.getBalance(transfer.getAccountFrom());
            BigDecimal receivingBalance = accountDao.getBalance(transfer.getAccountTo());


            accountDao.updateBalance((sendingBalance.subtract(transferAmount)), accountFrom);


            accountDao.updateBalance(receivingBalance.add(transferAmount), accountTo);
        }
        else {
            throw new ResponseStatusException(HttpStatus.CREATED);

        }
    }

}
