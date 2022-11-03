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

    public Account getBalance(int accountId) {
        String sql = "SELECT account_id, user_id, balance FROM account WHERE account_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, accountId);
        if(results.next()) {
            return mapRowToAccount(results);
        }
        throw new UsernameNotFoundException("User not found");
    }

    public Account mapRowToAccount(SqlRowSet rs) {
        Account account = new Account();
        account.setAccountId(rs.getInt("account_id"));
        account.setUserId(rs.getInt("user_id"));
        account.setBalance(rs.getBigDecimal("balance"));
        return account;
    }
    
}
