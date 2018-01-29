/**
 * 
 */
package com.madoka.sunb0002.webapi.profile;

import com.madoka.sunb0002.repositories.entities.User;
import com.madoka.sunb0002.webapi.AbstractResponse;

import io.swagger.annotations.ApiModel;

/**
 * @author Sun Bo
 *
 */
@ApiModel(description = "Profile API response")
public class ProfileResponse extends AbstractResponse<User> {

	public ProfileResponse(Integer status, User data, String msg) {
		super(status, data, msg);
	}

	@Override
	public String toString() {
		return "ProfileResponse [status=" + status + ", data=" + data + ", msg=" + msg + "]";
	}

}
