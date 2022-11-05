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
    public List<Transfer> listTransfers(Principal principal){
        String userName = principal.getName();
        int userId = userDao.findIdByUsername(userName);
        return transferDao.listTransfers(userId);
    }
    @RequestMapping(path = "/transfer/list/pending", method = RequestMethod.GET)
    public List<Transfer> listPendingTransfers(Principal principal) {
        String userName = principal.getName();
        int userId = userDao.findIdByUsername(userName);
        return transferDao.listPendingTransfers(userId);
    }
    @RequestMapping(path = "/transfer/choice", method = RequestMethod.PUT)
    public void requestTransferResponse(Principal principal, @Valid @RequestBody Transfer transfer) {
        String userName = principal.getName();
        int userId = userDao.findIdByUsername(userName);
        int transferId = transfer.getTransferId();
        int statusId = transfer.getTransferStatusId();
        int userTo = transferDao.pullUserTo(transferId);
        BigDecimal amount = transferDao.pullTransferAmount(transferId);
        BigDecimal fromBalance = accountDao.getBalance(userId);
        BigDecimal toBalance = accountDao.getBalance(userTo);
        if (statusId == 2) {
             if (fromBalance.compareTo(amount) < 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You can't send more money than what's in your account");
            }
             else {
                 transferDao.approveRequestTransfer(userId, transferId);
                 accountDao.updateBalance(fromBalance.subtract(amount), userId);
                 accountDao.updateBalance(toBalance.add(amount), userTo);
             }
        } else if (statusId == 3) {
            transferDao.denyRequestTransfer(userId, transferId);
        }
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
    @RequestMapping(path = "/transfer/send", method = RequestMethod.POST)
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

    @RequestMapping(path = "/transfer/request", method = RequestMethod.POST)
    public void createRequestTransfer(@Valid @RequestBody Transfer transfer){
        int accountFrom = transfer.getUserFrom();
        int accountTo = transfer.getUserTo();
        BigDecimal transferAmount = transfer.getAmount();
        BigDecimal fromBalance = accountDao.getBalance(accountFrom);
        BigDecimal toBalance = accountDao.getBalance(accountTo);
        BigDecimal minimumAmount = new BigDecimal(0);
       if(accountFrom == accountTo){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You can't request money to yourself");
        }
        else if(transferAmount.compareTo(minimumAmount) <= 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You can't request zero or negative amount");
        }
        else if (accountFrom != accountTo){
            transferDao.createRequestTransfer(transfer);
        }

    }



}
