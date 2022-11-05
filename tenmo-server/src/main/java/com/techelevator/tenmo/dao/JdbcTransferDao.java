package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDao implements TransferDao{

    private JdbcTemplate jdbcTemplate;

    public JdbcTransferDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Integer createSendTransfer(Transfer transfer){
        String sql = "INSERT INTO transfer (transfer_type_id, transfer_status_id, user_from, user_to, amount) " +
                     "VALUES (2, 2, ?, ?, ?) " +
                     "RETURNING transfer_id;";
        return jdbcTemplate.queryForObject(sql, Integer.class, transfer.getUserFrom(), transfer.getUserTo(), transfer.getAmount());
    }
    @Override
    public Integer createRequestTransfer(Transfer transfer){
        String sql = "INSERT INTO transfer (transfer_type_id, transfer_status_id, user_from, user_to, amount) " +
                "VALUES (1, 1, ?, ?, ?) " +
                "RETURNING transfer_id;";
        return jdbcTemplate.queryForObject(sql, Integer.class, transfer.getUserFrom(), transfer.getUserTo(), transfer.getAmount());
    }
    @Override
    public void approveRequestTransfer(int userId, int transferId){
        String sql = "UPDATE transfer " +
                     "SET transfer_status_id = 2 " +
                     "WHERE user_from = ? AND transfer_id = ?;";
         jdbcTemplate.update(sql, userId, transferId);
        }

    @Override
    public void denyRequestTransfer(int userId, int transferId){
        String sql = "UPDATE transfer " +
                "SET transfer_status_id = 3 " +
                "WHERE user_from = ? AND transfer_id = ?;";
        jdbcTemplate.update(sql, userId, transferId);
    }
    @Override
    public List<Transfer> listPendingTransfers(int userId) {
        List<Transfer> transferPendingList = new ArrayList<>();
        String sql = "SELECT transfer_id, transfer_type_id, transfer_status_id, user_from, user_to, amount " +
                "FROM transfer " +
                "WHERE transfer_status_id = 1 AND user_from = ? OR transfer_status_id = 1 AND user_to = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId, userId);
        while(results.next()){
            Transfer transfer = mapRowToTransfer(results);
            transferPendingList.add(transfer);
        }
        return transferPendingList;
    }
    @Override
    public List<Transfer> listTransfers(int userId) {
        List<Transfer> transferList = new ArrayList<>();
        String sql = "SELECT transfer_id, transfer_type_id, transfer_status_id, user_from, user_to, amount " +
                     "FROM transfer " +
                     "WHERE user_from = ? OR user_to = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId, userId);
        while(results.next()){
            Transfer transfer = mapRowToTransfer(results);
            transferList.add(transfer);
        }
        return transferList;
    }
    @Override
    public Integer pullUserTo(int transferId){
        String sql = "SELECT user_to " +
                     "FROM transfer " +
                     "WHERE transfer_id = ?;";
        return jdbcTemplate.queryForObject(sql, Integer.class, transferId);
    }
    @Override
    public BigDecimal pullTransferAmount(int transferId){
        String sql = "SELECT amount " +
                "FROM transfer " +
                "WHERE transfer_id = ?;";
        return jdbcTemplate.queryForObject(sql, BigDecimal.class, transferId);
    }
    @Override
    public Transfer findTransferById(int transferId) {
        Transfer transfer = null;
        String sql = "SELECT transfer_id, transfer_type_id, transfer_status_id, user_from, user_to, amount " +
                     "FROM transfer " +
                     "WHERE transfer_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transferId);
        if(results.next()){
            transfer = mapRowToTransfer(results);
        }
        return transfer;
    }

    public Transfer mapRowToTransfer(SqlRowSet rowSet) {
        Transfer transfer = new Transfer();
        transfer.setTransferId(rowSet.getInt("transfer_id"));
        transfer.setTransferTypeId(rowSet.getInt("transfer_type_id"));
        transfer.setTransferStatusId(rowSet.getInt("transfer_status_id"));
        transfer.setUserFrom(rowSet.getInt("user_from"));
        transfer.setUserTo(rowSet.getInt("user_to"));
        transfer.setAmount(rowSet.getBigDecimal("amount"));
        return transfer;
    }

}
