package com.appspot.deuvarneyjava.services;

import java.util.ArrayList;
import java.util.Collections;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;

import net.sf.jsr107cache.Cache;
import net.sf.jsr107cache.CacheException;
import net.sf.jsr107cache.CacheFactory;
import net.sf.jsr107cache.CacheManager;

public class MemCacheService {

	private Cache cache;
	private final static String all_posts = "ALL_POSTS";
	private final static String single_post = "SINGLE_POST_";
	private final static String latest_insert_id = "LATEST_ID";
	private final static String user_account = "USER_";

	public MemCacheService() {

		try {
			CacheFactory cacheFactory = CacheManager.getInstance()
					.getCacheFactory();
			cache = cacheFactory.createCache(Collections.emptyMap());
		} catch (CacheException e) {
			e.printStackTrace();
		}
	}

	public void writeToCacheAllPosts(Iterable<Entity> iterableEntity) {

		// Arraylist can be cast to Iterable but not vice-versa
		ArrayList<Entity> arrayEntity = new ArrayList<Entity>();
		for (Entity e : iterableEntity) {
			arrayEntity.add(e);
		}

		cache.put(all_posts, arrayEntity);
		System.out.println("Cache written");
	}

	public Iterable<Entity> readFromCacheAllPosts() {
		Iterable<Entity> value = (Iterable<Entity>) cache.get(all_posts);
		System.out.println("Cache Hit");
		return value;
		// System.out.println("Cache value is " + value);
	}

	public void clearAllPosts() {
		cache.remove(all_posts);
	}

	public void writeToCacheSinglePost(String postNumber, Entity postEntity) {
		System.out.println("Single Post Cache Write");
		cache.put(single_post + postNumber, postEntity);
	}

	public Entity readFromCacheSinglePost(String postNumber) {
		return (Entity) cache.get(single_post + postNumber);
	}

	public void writeToCacheLatestID(String latestID) {
		cache.put(latest_insert_id, latestID);
	}

	public String readFromCacheLatestId() {
		return (String) cache.get(latest_insert_id);
	}

	public void writeToCacheUser(String userName, Entity userData) {
		System.out.println("User Cache Write");
		cache.put(user_account + userName, userData);
	}

	public Entity readFromCacheUser(String userName) {
		System.out.println("User Cache Read");
		return (Entity) cache.get(user_account + userName);
	}

	public void writeToCacheRequireLoginNewPost(boolean access) {
		cache.put("adminRequireLoginNewPost", access);
	}

	public Object readFromCacheRequireLoginNewPost() {

		return cache.get("adminRequireLoginNewPost");
	}
	
	public void writeToCacheRequireAdminRights(boolean access) {
		cache.put("adminRequireAdminRights", access);
	}

	public Object readFromCacheRequireAdminRights() {

		return cache.get("adminRequireAdminRights");
	}

}
