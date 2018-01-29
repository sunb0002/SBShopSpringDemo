/**
 * 
 */
package com.madoka.sunb0002.webapi.profile;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.madoka.sunb0002.repositories.UserRepository;
import com.madoka.sunb0002.repositories.entities.User;
import com.madoka.sunb0002.services.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * @author Sun Bo
 *
 */
@Api
@RestController
@RequestMapping("/profile")
public class ProfileController {

	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepo;

	@ApiOperation(value = "getUserById", notes = "Get User by Id", tags = { "Profile" })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "User is found.", response = User.class),
			@ApiResponse(code = 404, message = "User is not found.", response = User.class),
			@ApiResponse(code = 500, message = "Unexpected Error occurred", response = User.class) })
	@RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
	public User getUserById(@PathVariable Long id) {
		LOGGER.info("Getting users with id: {}", id);
		return userRepo.findOne(id);
	}

	@ApiOperation(value = "searchUsersByName", notes = "Search Users with name", tags = { "Profile" })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Users are found.", response = User.class),
			@ApiResponse(code = 404, message = "Users are not found.", response = User.class),
			@ApiResponse(code = 500, message = "Unexpected Error occurred", response = User.class) })
	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public List<User> searchUsersByName(@RequestParam(value = "name", required = true) String name) {
		LOGGER.info("Getting users with name like: {}", name);
		return userService.getSomeUsersWithSimilarName(name);
	}

}
