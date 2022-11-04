package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@PreAuthorize("isAuthenticated()")
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
    @RequestMapping(path = "/transfer/list", method = RequestMethod.GET)
    public List<Transfer> transferList(Principal principal){
        String userName = principal.getName();
        int userId = userDao.findIdByUsername(userName);
        return transferDao.listTransfers(userId);
    }
    @RequestMapping(path = "/transfer/{id}", method = RequestMethod.GET)
    public Transfer transferForId(@PathVariable("id") int transferId){
        Transfer transfer = transferDao.findTransferById(transferId);

        if(transfer == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Transfer does not exist");
        }
        else {
            return transfer;
        }

    }
    @RequestMapping(path = "/transfer", method = RequestMethod.POST)
    public void createSendTransfer(@Valid @RequestBody Transfer transfer) {
          int accountFrom = transfer.getUserFrom();
          int accountTo = transfer.getUserTo();
          BigDecimal transferAmount = transfer.getAmount();
          BigDecimal fromBalance = accountDao.getBalance(accountFrom);
          BigDecimal toBalance = accountDao.getBalance(accountTo);
          if (accountFrom != accountTo && fromBalance.compareTo(transferAmount) >= 0){
              transferDao.createSendTransfer(transfer);
              accountDao.updateBalance(fromBalance.subtract(transferAmount), accountFrom);
              accountDao.updateBalance(toBalance.add(transferAmount), accountTo);
        }
          else if(accountFrom == accountTo){
              throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You can't send money to yourself");
          }
          else if(fromBalance.compareTo(transferAmount) < 0){
              throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You can't send more money than what's in your account");
          }
    }

}
