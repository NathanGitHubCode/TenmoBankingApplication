package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.math.BigDecimal;

public interface TransferDao {

    Integer createTransfer(Transfer transfer);
    Integer requestTransfer(Transfer transfer);
    void sendMoney(Transfer transfer);
}
