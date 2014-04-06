package com.appspot.deuvarneyjava.services;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;

import com.appspot.deuvarneyjava.blog.UserInfo;
import com.appspot.deuvarneyjava.services.MemCacheService;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.FetchOptions.Builder.*;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Text;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.memcache.MemcacheServicePb.MemcacheIncrementRequest.Direction;

/**
 * Used to manipulate entities in the Datastore.
 * 
 * @author dsanderson13
 * 
 */
public class MyDatastoreService {
	DatastoreService datastore;
	private MemCacheService mc;

	/**
	 * Used to add entities to the UserData Table.
	 * 
	 * @param username
	 *            User's desired user name.
	 * @param password
	 *            User's desired password.
	 * @param emailAddress
	 *            User's email address.
	 * @throws NoSuchAlgorithmException
	 */
	// public void add_user(String username, String password, String
	// emailAddress)
	// throws NoSuchAlgorithmException {
	//
	// // Key userDataKey = KeyFactory.createKey("userinfo", username);
	// // Entity userData = new Entity("userData", userDataKey);
	//
	// HashMap passwordHash = null;
	// passwordHash = SecurityHandlingService.generatePasswordHash(password);
	//
	// if (passwordHash != null) {
	// Entity userData = new Entity("userData");// , username);
	// userData.setProperty("username", username);
	// userData.setProperty("passwordHash",
	// passwordHash.get("passwordHash"));
	// userData.setProperty("passwordRandomValues",
	// passwordHash.get("RandomizedPassword"));
	// userData.setProperty("emailAddress", emailAddress);
	//
	// datastore.put(userData);
	// }
	//
	// }

	public MyDatastoreService() {
		mc = new MemCacheService();
		datastore = DatastoreServiceFactory.getDatastoreService();
	}

	public void add_user(String userName, String password, String emailAddress)
			throws NoSuchAlgorithmException {

		// Key userDataKey = KeyFactory.createKey("userinfo", username);
		// Entity userData = new Entity("userData", userDataKey);

		UserInfo passwordHash = null;
		passwordHash = SecurityHandlingService.generatePasswordHash(password);

		if (passwordHash != null) {
			Entity userData = new Entity("userData");// , username);
			userData.setProperty("username", userName);
			userData.setProperty("passwordHash", passwordHash.getPasswordHash());
			userData.setProperty("passwordRandomValues",
					passwordHash.getRandomizedPassword());
			userData.setProperty("emailAddress", emailAddress);
			userData.setProperty("createdDateTime", new Date());
			userData.setProperty("userCookie",
					SecurityHandlingService.generateUserCookieHash(userName));

			mc.writeToCacheUser(userName, userData);// Recently Implemented
			datastore.put(userData);
		}

	}

	/**
	 * Adds a post to the DataStore
	 * 
	 * @param subject
	 *            The subject the user wishes to add.
	 * @param content
	 *            The content a user wishes to add.
	 */
	public void add_post(String subject, String content) {
		Key postKey = KeyFactory.createKey("posts", subject);
		Text contentText = new Text(CommonService.escapeHTML(content));// .replace("\n", "<br>"));
		Entity newPost = new Entity("posts", postKey);
		newPost.setProperty("subject", subject);
		newPost.setProperty("content", contentText);// contentText);

		String insertID = getNextAvailableInsertId();
		newPost.setProperty("insert_id", insertID);

		newPost.setProperty("createdDateTime", new Date());

		mc.writeToCacheLatestID(insertID);
		mc.writeToCacheSinglePost(insertID, newPost);
		mc.clearAllPosts();// Empty out all_posts entry in cache to prevent
							// stale results

		datastore.put(newPost);

	}

	/**
	 * Used to query the items to be displayed on the blog home page.
	 * 
	 * @return Lists of entities to be displayed.
	 */
	public Iterable<Entity> queryAllPosts() {
		Iterable<Entity> cacheRead = mc.readFromCacheAllPosts();

		if (cacheRead == null) {
			Query q = new Query("posts").addSort("insert_id",
					SortDirection.DESCENDING);
			PreparedQuery pq = datastore.prepare(q);

			// Testing memcache

			// mc.writeToCache();
			System.out.println("Database hit\n");
			Iterable<Entity> entities = pq.asIterable();
			System.out.println("Test1");

			System.out.println("Test2");
			mc.writeToCacheAllPosts(entities);
			System.out.println("Test3");

			return entities;
		}
		System.out.println("Skipping DB");
		return cacheRead;
	}

	/**
	 * Queries a single post
	 * 
	 * @param post
	 *            Post to be queried
	 * @return A List of Entities.
	 */
	public Entity querySinglePost(String postNumber) {

		Entity cacheRead = mc.readFromCacheSinglePost(postNumber);
		if (cacheRead == null) {
			Query q = new Query("posts").addFilter("insert_id",
					FilterOperator.EQUAL, postNumber);
			PreparedQuery pq = datastore.prepare(q);
			System.out.println("Single post DB Read");
			Entity dbResult = pq.asSingleEntity();
			mc.writeToCacheSinglePost(postNumber, dbResult);

			return dbResult;
		}
		System.out.println("Skipping Single Post DB Read");
		return cacheRead;
	}

	/**
	 * Queries the Next available insertId number to be used when creating a new
	 * post
	 * 
	 * @return Next available insertID.
	 */
	public String getNextAvailableInsertId() {
		// ## Orignal method for getting the next available insert id
		// Query q = new Query("posts").addFilter("insert_id",
		// FilterOperator.GREATER_THAN_OR_EQUAL, "100").addSort(
		// "insert_id", SortDirection.DESCENDING);
		// PreparedQuery pq = datastore.prepare(q);
		//
		// Iterable<Entity> e = pq.asIterable();
		// int currentId = 0;
		//
		// for (Entity f : e) {
		// currentId = Integer.parseInt((String) (f.getProperty("insert_id")));
		// System.out.println("The current number is " + currentId);
		// int newId = currentId + 1;
		// System.out.println(pq.asIterable());
		// System.out.println(newId);
		// return Integer.toString(newId);
		// }
		//
		// // int newId = currentId + 1;
		// // System.out.println(pq.asIterable());
		// // System.out.println(newId);
		// // return Integer.toString(newId);
		// System.out.print("You have a serious issue here");
		// return "100";

		String currentId = getLatestInsertID();
		if (currentId != null) {
			int nextInsertId = Integer.parseInt(currentId) + 1;
			return Integer.toString(nextInsertId);
		}
		return "100";
	}

	/**
	 * Queries the most recently created insert ID
	 * 
	 * @return Most recently created insert ID
	 */
	public String getLatestInsertID() {

		String cacheLatestID = mc.readFromCacheLatestId();

		if (cacheLatestID == null) {
			Query q = new Query("posts").addFilter("insert_id",
					FilterOperator.GREATER_THAN_OR_EQUAL, "100").addSort(
					"insert_id", SortDirection.DESCENDING);
			PreparedQuery pq = datastore.prepare(q);

			// ## Original method for getting the latest insert id
			// Iterable<Entity> e = pq.asIterable();
			// int currentId;
			// for (Entity f : e) {
			// currentId = Integer.parseInt((String)
			// (f.getProperty("insert_id")));
			// System.out.println("The current number is " + currentId);
			// return Integer.toString(currentId);
			// }
			// return null;
			int currentId = 0;
			if (pq.countEntities() >= 1) {

				Iterable<Entity> e = pq.asIterable();
				for (Entity f : e) {
					currentId = Integer.parseInt((String) f
							.getProperty("insert_id"));
					System.out.println("The current number is " + currentId);
					break;
				}
				String dbResult = Integer.toString(currentId);
				mc.writeToCacheLatestID(dbResult);
				return dbResult;
			}
		}
		System.out.print("Skipping Latest ID Db Read");
		return cacheLatestID;
	}

	/**
	 * Verifies users login credentials.
	 * 
	 * @param userName
	 *            User's user name.
	 * @param password
	 *            User's password.
	 * @return A boolean detailing whether the entered credentials matches a
	 *         current user.
	 */
	/*
	 * public boolean verifyUser(String userName, String password) { Query q =
	 * new Query("userData").addFilter("username", FilterOperator.EQUAL,
	 * userName); PreparedQuery pq = datastore.prepare(q); // Use for
	 * limits.....Iterable<Entity> rd = //
	 * pq.asIterable(FetchOptions.Builder.withLimit(0)); Entity results =
	 * pq.asSingleEntity();
	 * 
	 * if (results != null) {
	 * 
	 * HashMap hash = new HashMap(); hash.put("username",
	 * results.getProperty("username")); hash.put("passwordHash",
	 * results.getProperty("passwordHash")); hash.put("passwordRandomValues",
	 * results.getProperty("passwordRandomValues")); hash.put("userPassword",
	 * password); if (SecurityHandlingService.verifyPassword(hash)) {
	 * System.out.println("Authenticated"); return true; } } return false;
	 * 
	 * }
	 */

	public UserInfo verifyUser(String userName, String password) {
		Entity results = mc.readFromCacheUser(userName);
		if (results == null) {
			Query q = new Query("userData").addFilter("username",
					FilterOperator.EQUAL, userName);
			PreparedQuery pq = datastore.prepare(q);
			// Use for limits.....Iterable<Entity> rd =
			// pq.asIterable(FetchOptions.Builder.withLimit(0));
			results = pq.asSingleEntity();
			if (results != null) {// Fix this duplication later
				mc.writeToCacheUser(userName, results);
			}
		}

		if (results != null) {
			System.out.println("user is in DB");
			// HashMap hash = new HashMap();
			UserInfo info = new UserInfo();

			// hash.put("username", results.getProperty("username"));
			info.setUserName((String) results.getProperty("username"));

			// hash.put("passwordHash", results.getProperty("passwordHash"));
			info.setPasswordHash((String) results.getProperty("passwordHash"));

			// hash.put("passwordRandomValues",results.getProperty("passwordRandomValues"));
			// info.setRandomizedPassword("passwordRandomValues");
			info.setRandomizedPassword((String) results
					.getProperty("passwordRandomValues"));

			// hash.put("userPassword", password);
			info.setPassword(password);

			info.setUserCookie((String) results.getProperty("userCookie"));

			if (SecurityHandlingService.verifyPassword(info)) {
				System.out.println("Authenticated");
				return info;
			}
		}
		return null;

	}

	/**
	 * Confirms that the desired user name is not consumed in the database for
	 * registration.
	 * 
	 * @param userName
	 *            Desired user name.
	 * @return A boolean detailing availability of the user name.
	 */
	public boolean confirmNonExistingUser(String userName) {
		Entity cacheResult = mc.readFromCacheUser(userName);
		if (cacheResult == null) {
			Query q = new Query("userData").addFilter("username",
					FilterOperator.EQUAL, userName);
			PreparedQuery pq = datastore.prepare(q);
			return pq.countEntities() == 0;
		}
		return cacheResult == null;
	}

	/**
	 * Confirms that the desired email address is not consumed in the database
	 * for registration.
	 * 
	 * @param emailAddress
	 *            Desired email address.
	 * @return A boolean detailing availability of the email address.
	 */
	public boolean confirmNonExisitingEmail(String emailAddress) {
		Query q = new Query("userData").addFilter("emailAddress",
				FilterOperator.EQUAL, emailAddress);
		PreparedQuery pq = datastore.prepare(q);
		return pq.countEntities() == 0;

	}

	public String getUserCookie(String userCookieValue) {
		Query q = new Query("userData").addFilter("userCookie",
				FilterOperator.EQUAL, userCookieValue);
		PreparedQuery pq = datastore.prepare(q);
		// Use for limits.....Iterable<Entity> rd =
		// pq.asIterable(FetchOptions.Builder.withLimit(0));
		Entity results = pq.asSingleEntity();

		if (results != null) {
			System.out.println("User cookie: "
					+ results.getProperty("userCookie"));
			return (String) results.getProperty("userCookie");
		}
		return null;
	}
}
