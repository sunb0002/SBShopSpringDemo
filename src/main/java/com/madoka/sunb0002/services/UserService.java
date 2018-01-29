/**
 * 
 */
package com.madoka.sunb0002.services;

import java.util.List;

import com.madoka.sunb0002.common.dtos.UserDTO;
import com.madoka.sunb0002.common.exceptions.ServiceException;

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
	List<UserDTO> getSomeUsersWithSimilarName(String name);

	/**
	 * 
	 * @param userDto
	 * @return
	 */
	UserDTO saveUserProfile(UserDTO userDto) throws ServiceException;

}
