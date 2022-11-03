package com.techelevator.tenmo.model;

public class AuthenticatedUser {
	
	private String token;
	private User user;
	private Account account;
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
}
