package com.appspot.deuvarneyjava.services;

import java.util.regex.*;

/**Handles creation and verification for creating new user accounts.
 * @author dsanderson13
 *
 */
public class RegistrationService {
	
	private static final String EMAIL_PATTERN = 
			"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{1,})$";
	
	private static final String PASSWORD_PATTERN = "^.{3,20}$";
	private static final String USERNAME_PATTERN = "^[a-zA-Z0-9_-]{3,20}$";
	//private static final String EMAIL_PATTERN2 = "^[\s]$"; 
	private static final int userNameLength = 6;
	private static final int passwordLength = 6;
	
	/**Validates email address format.
	 * @param emailAddress Desired Email address.
	 * @return A boolean determining validity of the email address format.
	 */
	public boolean verifyEmail(String emailAddress){
		Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		Matcher matcher = pattern.matcher(emailAddress);
		return emailAddress.equals("") || matcher.matches();
	}
	
	
	/**Validates user name format.
	 * @param userName User's desired user name format.
	 * @return A boolean determining validity of the user name format.
	 */
	public boolean verifyUsername(String userName){
		//return (userName.length() >= userNameLength && !userName.contains(" "));
		Pattern pattern = Pattern.compile(USERNAME_PATTERN);
		Matcher matcher = pattern.matcher(userName);
		return matcher.matches();
	}
	
	/**Validates password format.
	 * @param password User's desired password.
	 * @return A boolean determining the validity of the password format.
	 */
	public boolean verifyPassword(String password){
		Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
		Matcher matcher = pattern.matcher(password);
		return matcher.matches();
	}
	
	/**Validates that the password and confirmation password are the same.
	 * @param password1 Password from first password field.
	 * @param password2 Password from second password field.
	 * @return A boolean determining whether the two passwords match.
	 */
	public boolean verifyMatchingPasswords(String password1, String password2){
		return (password1.equals(password2));
	}
}

