/**
 * 
 */
package com.madoka.sunb0002.webapi.internal;

import com.madoka.sunb0002.webapi.AbstractResponse;

import io.swagger.annotations.ApiModel;

/**
 * @author Sun Bo
 *
 */
@ApiModel(description = "Home/Internal API response")
public class HomeResponse extends AbstractResponse<String> {

	public HomeResponse(Integer status, String data, String msg) {
		super(status, data, msg);
	}

	@Override
	public String toString() {
		return "HomeResponse [status=" + status + ", data=" + data + ", msg=" + msg + "]";
	}
}
