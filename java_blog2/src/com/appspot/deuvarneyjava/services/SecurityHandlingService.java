package com.appspot.deuvarneyjava.services;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Random;



import com.appspot.deuvarneyjava.blog.UserInfo;
import com.appspot.deuvarneyjava.services.CommonService;

/**Class used for generating and verifying passwords and other secured hashes.
 * @author dsanderson13
 *
 */
public class SecurityHandlingService {
	private final static String secretPasswordPhrase = "Abc123";
	private final static String secretUserNamePhrase = "Qwerty";
	private final static String secretUserCookiePhrase = "Cookie1234";
	private final static int USERNAMERANDOMLENGTH = 5;


	/**Used to generate a the password hash value with the users password, master password, and randomly generated password to be stored in the database 
	 * @param password The user's password.
	 * @return A hashMap containing the Users password hash and random values used to create the Hash
	 */
	//Original 
//	public static HashMap generatePasswordHash2(String password) {
//		String randomizedUserNameString = getRandomString(USERNAMERANDOMLENGTH);
//		password = secretPasswordPhrase + password + randomizedUserNameString;
//		
//		try {
//			String hexString = encodeToHex(password);
//			HashMap hp = new HashMap();
//			hp.put("passwordHash", hexString);
//			hp.put("RandomizedPassword", randomizedUserNameString);
//			return hp;
//		
//		} catch (NoSuchAlgorithmException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		// return hexString.toString();
//		return null;
//
//	}
	
	public static UserInfo generatePasswordHash(String password) {
		
		
		String randomizedUserNameString = getRandomString(USERNAMERANDOMLENGTH);
		password = secretPasswordPhrase + password + randomizedUserNameString;
		
		try {
			String hexString = encodeToHex(password);

			UserInfo info = new UserInfo();
			info.setPasswordHash(hexString);
			info.setRandomizedPassword(randomizedUserNameString);
			return info;
		
		} catch (NoSuchAlgorithmException e) {

			e.printStackTrace();
		}
		// return hexString.toString();
		return null;

	}

	/**Used to verify a password entered by the user.
	 * @param hash Hashmap that contains the parameters "userPassword" and "passwordHash".
	 * @return A boolean which identifies whether the user entered the correct password.
	 */
	public static boolean verifyPassword(UserInfo hash) {
		//String userPassword = (String) hash.get("userPassword");
		String userPassword = hash.getPassword();
		
		//String passwordHash = (String) hash.get("passwordHash");
		String passwordHash = hash.getPasswordHash();
		
		//String passwordRandomValues = (String) hash.get("passwordRandomValues");
		String passwordRandomValues = (String) hash.getRandomizedPassword();
		
		String fullPassword = secretPasswordPhrase + userPassword + passwordRandomValues;
		try {
			if(passwordHash.equals(encodeToHex(fullPassword))){
				return true;
			}
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;

	}

	/**Used for creating randomly generated strings in order to create complex hashes.
	 * @param Length The length desired length of a randomly generated string.
	 * @return Randomly generated string.
	 */
	public static String getRandomString(int length) {
		final String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJLMNOPQRSTUVWXYZ1234567890";
		String output = "";

		// Random ran = new Random();
		for (int i = 0; i < length; i++) {
			output += base.charAt(new Random().nextInt(base.length()));
		}

		return output;
	}
	
	/**Used to encode a password or phrase into a hexadecimal value.
	 * @param password The password or phrase to be encoded.
	 * @return Encoded password or phrase.
	 * @throws NoSuchAlgorithmException.
	 */
	private static String encodeToHex(String password) throws NoSuchAlgorithmException{
		MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
		byte[] passBytes = password.getBytes();
		byte[] passHash = sha256.digest(passBytes);

		StringBuffer hexString = new StringBuffer();

		for (int i = 0; i < passHash.length; i++) {
			String hex = Integer.toHexString(0xff & passHash[i]);
			if (hex.length() == 1)
				hexString.append('0');
			hexString.append(hex);
		}
		return hexString.toString();
	}
	
	/**Generates a user name cookie hash to be added in the response header and implements a secret cookie phrase and the user's user name.
	 * @param userName the user's user name.
	 * @return a string with the hash value of the user name for adding as a cookie for user authentication.
	 */
	public static String generateUserCookieHash(String userName){
		try {
			return userName + "|" + encodeToHex(secretUserCookiePhrase + userName);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}

}
