/**
 * 
 */
package com.madoka.sunb0002.webapi;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.madoka.sunb0002.webapi.internal.HomeControllerTest;
import com.madoka.sunb0002.webapi.profile.ProfileControllerTest;

/**
 * @author Sun Bo
 *
 */
@RunWith(Suite.class)
@SuiteClasses({ HomeControllerTest.class, ProfileControllerTest.class })
public class ControllerTestSuite {

}
