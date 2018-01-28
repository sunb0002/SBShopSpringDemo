/**
 * 
 */
package com.madoka.sunb0002.webapi.internal;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

/**
 * @author Sun Bo
 *
 */
public class HomeControllerTest {

	@InjectMocks
	private HomeController controller;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	/**
	 * Test method for
	 * {@link com.madoka.sunb0002.webapi.internal.HomeController#allHail()}.
	 */
	@Test
	public void testAllHail() throws Exception {
		HomeResponse hr = controller.allHail();
		assertEquals(200, (int) hr.getStatus());
	}

}
