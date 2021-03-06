package com.appspot.deuvarneyjava.blog;

import java.util.Date;

public class UserInfo {
	private String userName, password, verifyPassword, email;
	private String passwordHash, randomizedPassword, userCookie;
	private boolean admin;
	private Date createdDate;
	
	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public String getRandomizedPassword() {
		return randomizedPassword;
	}

	public void setRandomizedPassword(String randomizedPassword) {
		this.randomizedPassword = randomizedPassword;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getVerifyPassword() {
		return verifyPassword;
	}

	public void setVerifyPassword(String verifyPassword) {
		this.verifyPassword = verifyPassword;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserCookie() {
		return userCookie;
	}

	public void setUserCookie(String userCookie) {
		this.userCookie = userCookie;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	
}