package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class JdbcTransferDao implements TransferDao{

    private JdbcTemplate jdbcTemplate;

    public JdbcTransferDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Integer createTransfer(Transfer transfer){
        String sql = "INSERT INTO transfer (transfer_type_id, transfer_status_id, account_from, account_to, amount) " +
                     "VALUES (2, 2, ?, ?, ?) " +
                     "RETURNING transfer_id;";
        Integer newId = jdbcTemplate.queryForObject(sql, Integer.class, transfer.getAccountFrom(), transfer.getAccountTo(), transfer.getAmount());
//        try {
//            newId = jdbcTemplate.queryForObject(sql, Integer.class, accountFrom, accountTo, amount);
//        }
//        catch (DataAccessException e){
//            return false;
//        }
        return newId;
    }

    @Override
    public Integer requestTransfer(Transfer transfer) {
        String sql = "INSERT INTO transfer (transfer_type_id, transfer_status_id, account_from, account_to, amount) " +
                     "VALUES (1, 1, ?, ?, ?) " +
                     "RETURNING transfer_id;";
        Integer newId = jdbcTemplate.queryForObject(sql, Integer.class, transfer.getAccountFrom(), transfer.getAccountTo(), transfer.getAmount());;
//        try {
//            newId = jdbcTemplate.queryForObject(sql, Integer.class, accountFrom, accountTo, amount);
//        }
//        catch (DataAccessException e) {
//            return false;
//        }
        return newId;
    }

    @Override
    public void sendMoney(Transfer transfer) {

    }
}
