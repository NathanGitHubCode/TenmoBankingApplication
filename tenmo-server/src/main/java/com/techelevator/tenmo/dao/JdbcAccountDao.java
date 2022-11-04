package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class JdbcAccountDao implements AccountDao{

    private final JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public BigDecimal getBalance(int userId) {
        String sql = "SELECT balance FROM account WHERE user_id = ?;";
        return jdbcTemplate.queryForObject(sql, BigDecimal.class, userId);
    }

    @Override
    public boolean updateBalance(BigDecimal balance, int userId) {
        String sql = "UPDATE account SET balance = ? WHERE user_id = ?;";
        return jdbcTemplate.update(sql, balance, userId) == 1;
    }

    @Override
    public int findAccountIdByUsername(String username){
            String sql = "SELECT a.account_id FROM account AS a " +
                         "JOIN tenmo_user AS tu ON tu.user_id = a.user_id WHERE username ILIKE ?;";
            Integer id = jdbcTemplate.queryForObject(sql, Integer.class, username);
            if (id != null) {
                return id;
            } else {
                return -1;
            }
    }



    public Account mapRowToAccount(SqlRowSet rs) {
        Account account = new Account();
        account.setAccountId(rs.getInt("account_id"));
        account.setUserId(rs.getInt("user_id"));
        account.setBalance(rs.getBigDecimal("balance"));
        return account;
    }
    
}
