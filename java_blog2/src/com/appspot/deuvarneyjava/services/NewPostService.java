package com.appspot.deuvarneyjava.services;

/**Verifies the user entered valid information for creating a new post
 * @author dsanderson13
 *
 */
public class NewPostService {

	/**Verifies a valid subject.
	 * @param subject Subject which the user desires to add.
	 * @return A boolean detailing validity of the subject.
	 */
	public boolean verifySubject(String subject) {
		if (subject.trim().length() > 0) {
			return true;
		}
		return false;
	}

	/**Verifies valid content.
	 * @param content Content the user desires to add.
	 * @return A boolean detailing validity of the content.
	 */
	public boolean verifyContent(String content){
		if (content.trim().length() >0){
			return true;
		}
		return false;
	}
}
