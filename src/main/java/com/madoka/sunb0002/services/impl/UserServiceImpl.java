/**
 * 
 */
package com.madoka.sunb0002.services.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.madoka.sunb0002.repositories.UserRepository;
import com.madoka.sunb0002.repositories.entities.User;
import com.madoka.sunb0002.services.UserService;

/**
 * @author Sun Bo
 *
 */
@Service
public class UserServiceImpl implements UserService {

	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private UserRepository userRepo;

	@Override
	@Transactional(value = "sbshop-txnmgr", readOnly = true)
	public List<User> getSomeUsersWithSimilarName(String name) {
		LOGGER.info("Service is searching users with name like: {}", name);
		return userRepo.findNricByNameLikeUsingQuery(name);
	}

	@Override
	@Transactional("sbshop-txnmgr")
	public User saveUserProfile(User user) {
		LOGGER.info("Service is updating user with latest profile: {}", user);
		return userRepo.save(user);
	}

}
