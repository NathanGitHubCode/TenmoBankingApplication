package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.security.auth.login.AccountNotFoundException;
import java.security.Principal;

public class JdbcAccountDao implements AccountDao{

    private  JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Account getBalance(Principal principal) {
        String sql = "SELECT balance FROM account WHERE account_id = ?";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, principal.getName());
        return mapRowToAccount(rowSet);
    }

    public Account mapRowToAccount(SqlRowSet rs) {
        Account account = new Account();
        account.setAccount_id(rs.getInt("account_id"));
        account.setUser_id(rs.getInt("user_id"));
        account.setBalance(rs.getDouble("balance"));
        return account;
    }


}
