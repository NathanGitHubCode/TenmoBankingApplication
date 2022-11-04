package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

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

    @RequestMapping(path = "/users", method = RequestMethod.GET)
    public List<User> userList(){
        return userDao.findAll();
    }
    @RequestMapping(path = "/transfers", method = RequestMethod.GET)
    public List<Transfer> transferList(Principal principal){
        String userName = principal.getName();
        int accountId = accountDao.findAccountIdByUsername(userName);
        return transferDao.listTransfers(accountId);
    }

    @RequestMapping(path = "/transfer", method = RequestMethod.POST)
    public void createSendTransfer(@Valid @RequestBody Transfer transfer) {
          int accountFrom = transfer.getAccountFrom();
          int accountTo = transfer.getAccountTo();
//        BigDecimal transferAmount = transfer.getAmount();
//        BigDecimal sendingBalance = accountDao.getBalance(transfer.getAccountFrom());
//        BigDecimal receivingBalance = accountDao.getBalance(transfer.getAccountTo());
          if (accountFrom != accountTo) {
            transferDao.createSendTransfer(transfer);
//            accountDao.updateBalance((sendingBalance.subtract(transferAmount)), accountFrom);
//            accountDao.updateBalance((receivingBalance.add(transferAmount)), accountTo);
        }
    }

//    @RequestMapping(path = "/transfer", method = RequestMethod.POST)
//    public void sendTransfer(@Valid @RequestBody TransferDTO transfer) {
//        int accountFrom = transfer.getAccountFrom();
//        int accountTo = transfer.getAccountTo();
//        if(accountTo != accountFrom){
//            BigDecimal transferAmount = transfer.getAmount();
//            BigDecimal sendingBalance = accountDao.getBalance(transfer.getAccountFrom());
//            BigDecimal receivingBalance = accountDao.getBalance(transfer.getAccountTo());
//
//            accountDao.updateBalance((sendingBalance.subtract(transferAmount)), accountFrom);
//
//            accountDao.updateBalance(receivingBalance.add(transferAmount), accountTo);
//        }
//        else {
//            throw new ResponseStatusException(HttpStatus.CREATED);
//
//        }
//    }

}
