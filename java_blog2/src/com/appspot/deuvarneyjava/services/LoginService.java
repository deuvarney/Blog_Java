package com.appspot.deuvarneyjava.services;

import com.appspot.deuvarneyjava.blog.UserInfo;



/**Handles User Login authentication
 * @author dsanderson13
 *
 */
public class LoginService {
	private UserInfo info;
	private String userName;
	private MyDatastoreService datastore;


	/**Authenticates a user's credentials for logging into the web site.
	 * @param userName User's entered user name.
	 * @param password User's entered password
	 * @return A boolean indicating a users login credentials.
	 */
	public UserInfo authenticate_user(String userName, String password){
		
		info= new MyDatastoreService().verifyUser(userName, password);
		
		if (info != null){
			return info;
		}
		return null;
	}
	
	public String authenticateUserCookie(String cookieValue){
		if(new MyDatastoreService().getUserCookie(cookieValue) != null){
			userName = cookieValue.substring(0,cookieValue.indexOf("|"));
			
			System.out.println(cookieValue.substring(0,cookieValue.indexOf("|")));
			System.out.println("Username is: " +userName);
			return cookieValue.substring(0, cookieValue.indexOf("|"));
			
		}
		return null;
	}
	
	public boolean isAdministrator(){
		System.out.println("Inside of is Administrator-Username is: " +userName);
		 UserInfo userInfo = new MyDatastoreService().queryUser(userName);	
		 System.out.println("Userinfo to stirng" + userInfo.toString());
		 return userInfo.isAdmin();
	}
}
