package application;

import java.text.SimpleDateFormat;
import java.util.Date;

public class User extends Main {

	private String username;
	private String password;
	private boolean isAdmin;
	private Date lastLogin;

	public User(String username, String password,boolean isAdmin){
		this.username = username;
		this.password = password;
		this.isAdmin = isAdmin;
		lastLogin = null;
	}

	/**
	 * @return username
	 */
	public String getUsername() {
		return username;
	}

	/** 
	 * @return password
	 */
	public String getPassword() {
		return password;    	
	}

	/**
	 * @return true if user is administrator
	 */
	public boolean getIsAdmin() {
		return isAdmin;
	}

	/**
	 * @return time stamp of last login occurrence
	 */
	public String getLastLogin(){
		if (lastLogin != null){
			SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
			return ft.format(lastLogin);
		} else {
			return "";
		}
	}

	/**
	 * Sets the last login time stamp
	 */
	public void setLastLogin(){
		this.lastLogin = new Date();
	}

	/** 
	 * set new username
	 * @param new username
	 */
	public void setUsername(String user){
		this.username = user;
	}

	/** 
	 * set new password
	 * @param password password associated with new user
	 */
	public void setPassword(String password){
		this.password = password;
	}
	/** 
	 * set new user access level
	 * @param isAdmin if true, user is administrator
	 */
	public void setIsAdmin(boolean isAdmin){
		this.isAdmin = isAdmin;

	}
}
