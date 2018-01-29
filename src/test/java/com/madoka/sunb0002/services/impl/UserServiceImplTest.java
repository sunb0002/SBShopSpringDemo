package com.madoka.sunb0002.services.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import com.madoka.sunb0002.common.dtos.UserDTO;
import com.madoka.sunb0002.repositories.UserRepository;
import com.madoka.sunb0002.repositories.entities.User;
import com.madoka.sunb0002.services.DtoParserService;
import com.madoka.sunb0002.services.UserService;

public class UserServiceImplTest {

	@Mock
	private UserRepository userRepo;
	@Spy
	private DtoParserService dtoParserSvc = new DtoParserServiceImpl();
	@InjectMocks
	private UserService userService = new UserServiceImpl();

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testSaveUserProfile() throws Exception {
		UserDTO inputDto = new UserDTO(null, "nric1", "name1");
		User outputUser = new User();
		outputUser.setUserId(1L);
		outputUser.setNric(inputDto.getNric());
		outputUser.setName(inputDto.getName());

		when(userRepo.findOne(any(Long.class))).thenReturn(null);
		when(userRepo.save(any(User.class))).thenReturn(outputUser);

		UserDTO outputDto = userService.saveUserProfile(inputDto);
		assertEquals(outputUser.getUserId(), outputDto.getUserId());
		assertEquals(outputUser.getNric(), outputDto.getNric());
		assertEquals(outputUser.getName(), outputDto.getName());
	}

	@Test
	public void testGetSomeUsersWithSimilarName() throws Exception {
		// throw new RuntimeException("not yet implemented");
	}

}
