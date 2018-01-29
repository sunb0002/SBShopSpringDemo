/**
 * 
 */
package com.madoka.sunb0002.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.madoka.sunb0002.common.dtos.UserDTO;
import com.madoka.sunb0002.repositories.entities.User;
import com.madoka.sunb0002.services.DtoParserService;

/**
 * @author Sun Bo
 *
 */
@Service
public class DtoParserServiceImpl implements DtoParserService {

	@Override
	public UserDTO parseUser(User user) {
		return new UserDTO(user.getUserId(), user.getNric(), user.getName());
	}

	@Override
	public User parseUserDTO(UserDTO userDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserDTO> parseUsers(List<User> users) {
		List<UserDTO> dtos = new ArrayList<>();
		for (User user : users) {
			dtos.add(parseUser(user));
		}
		return dtos;
	}

}
