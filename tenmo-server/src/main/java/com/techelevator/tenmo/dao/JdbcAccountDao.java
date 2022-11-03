package com.techelevator.tenmo.dao;

import ch.qos.logback.core.db.DataSourceConnectionSource;
import com.techelevator.tenmo.model.Account;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.security.auth.login.AccountNotFoundException;
import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;

@Component
public class JdbcAccountDao implements AccountDao{

    private final JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public BigDecimal getBalance(int userId) {

        String sql = "SELECT balance FROM account WHERE user_id = ?;";
        BigDecimal results = jdbcTemplate.queryForObject(sql, BigDecimal.class, userId);
        return results;
    }

    @Override
    public void updateBalance(BigDecimal balance, int account) {
        String sql = "UPDATE account SET balance = ? WHERE account_id = ?";
        jdbcTemplate.update(sql, balance, account);
    }

    @Override
    public int findAccountIdByUsername(String username){
            String sql = "SELECT a.account_id, tu.user_id, tu.username FROM account AS a " +
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
