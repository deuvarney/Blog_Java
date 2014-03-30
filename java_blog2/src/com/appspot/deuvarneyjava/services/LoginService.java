package com.appspot.deuvarneyjava.services;

import com.appspot.deuvarneyjava.blog.UserInfo;

//import com.

/**Handles User Login authentication
 * @author dsanderson13
 *
 */
public class LoginService {
	


	/**Authenticates a user's credentials for logging into the web site.
	 * @param userName User's entered user name.
	 * @param password User's entered password
	 * @return A boolean indicating a users login credentials.
	 */
	public UserInfo authenticate_user(String userName, String password){
		UserInfo info= new MyDatastoreService().verifyUser(userName, password);
		
		if (info != null){
			return info;
		}
		return null;
	}
	
	public String authenticateUserCookie(String cookieValue){
		if(new MyDatastoreService().getUserCookie(cookieValue) != null){
			
			System.out.println(cookieValue.substring(0, 
					cookieValue.indexOf("|")));
			
			return cookieValue.substring(0, 
					cookieValue.indexOf("|"));
			
		}
		return null;
	}
}
