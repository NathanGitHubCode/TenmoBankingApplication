package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.math.BigDecimal;
import java.util.List;

public interface TransferDao {

    Integer createSendTransfer(Transfer transfer);
    Integer createRequestTransfer(Transfer transfer);
    List<Transfer> listTransfers(int userId);
    List<Transfer> listPendingTransfers(int userId);
    Transfer findTransferById(int transferId);
    void approveRequestTransfer(int userId, int transferId);
    void denyRequestTransfer(int userId, int transferId);
    Integer pullUserTo(int transferId);
    BigDecimal pullTransferAmount(int transferId);
}
