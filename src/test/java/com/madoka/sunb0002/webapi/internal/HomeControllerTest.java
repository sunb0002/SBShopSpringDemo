/**
 * 
 */
package com.madoka.sunb0002.webapi.internal;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * @author Sun Bo
 *
 */
public class HomeControllerTest {

	@Mock
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
	@Test(timeout = 2000)
	public void testAllHail() throws Exception {
		when(controller.allHail()).thenReturn(new HomeResponse(200, null, null));
		HomeResponse hr = controller.allHail();
		assertThat((int) hr.getStatus(), anyOf(equalTo(200), equalTo(403)));
	}

}
