package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.math.BigDecimal;
import java.util.List;

public interface TransferDao {

    Integer createSendTransfer(Transfer transfer);
    List<Transfer> listTransfers(int accountId);
}
