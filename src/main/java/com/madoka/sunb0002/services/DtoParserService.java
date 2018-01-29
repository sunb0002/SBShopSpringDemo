/**
 * 
 */
package com.madoka.sunb0002.services;

import java.util.List;

import com.madoka.sunb0002.common.dtos.UserDTO;
import com.madoka.sunb0002.repositories.entities.User;

/**
 * @author Sun Bo
 *
 */
public interface DtoParserService {

	/**
	 * 
	 * @param user
	 * @return
	 */
	UserDTO parseUser(User user);

	/**
	 * 
	 * @param userDto
	 * @return
	 */
	User parseUserDTO(UserDTO userDto);

	/**
	 * 
	 * @param users
	 * @return
	 */
	List<UserDTO> parseUsers(List<User> users);

}
