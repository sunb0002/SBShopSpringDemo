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

	// @Test
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

	// @Test
	public void testGetSomeUsersWithSimilarName() throws Exception {
		// throw new RuntimeException("not yet implemented");
	}

	/**
	 * Something not relevant :)
	 * 
	 * @throws Exception
	 */
	@Test
	public void testConverter() throws Exception {
		assertEquals(2147483647, converter("2147483647.5"));
		assertEquals(123, converter("123.333"));
		assertEquals(-321, converter("-321"));
		assertEquals(-321, converter("-321.333"));
		assertEquals("Empty input", getConverterEx(null));
		assertEquals("Non-numeric input", getConverterEx("abcd"));
		assertEquals("Integer overflow", getConverterEx("89234234423423423432"));
	}

	private String getConverterEx(String s) {
		try {
			converter(s);
		} catch (Exception e) {
			return e.getMessage();
		}
		return null;
	}

	public int converter(String s) throws Exception {

		if (s == null || s.length() == 0) {
			throw new Exception("Empty input");
		}
		if (!s.matches("^[-+]?\\d+(\\.\\d+)?$")) {
			throw new Exception("Non-numeric input");
		}

		long result = 0L;

		char[] arr = s.toCharArray();
		boolean isNeagtive = s.indexOf('-') > -1;
		int dotIdx = s.indexOf('.');
		if (dotIdx < 0) {
			dotIdx = s.length();
		}

		for (int i = (isNeagtive ? 1 : 0); i < dotIdx; i++) {

			char ch = arr[i];

			int digit = ch - 48;
			result += (long) (digit * Math.pow(10, dotIdx - 1 - i));
			if (result > Integer.MAX_VALUE) {
				throw new Exception("Integer overflow");
			}
		}

		return (int) (isNeagtive ? -result : result);
	}

}
