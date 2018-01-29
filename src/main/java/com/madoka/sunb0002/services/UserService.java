/**
 * 
 */
package com.madoka.sunb0002.services;

import java.util.List;

import com.madoka.sunb0002.repositories.entities.User;

/**
 * @author Sun Bo
 *
 */
public interface UserService {

	/**
	 * 
	 * @param name
	 * @return
	 */
	List<User> getSomeUsersWithSimilarName(String name);

	/**
	 * 
	 * @param user
	 * @return
	 */
	User saveUserProfile(User user);

}
