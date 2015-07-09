package br.com.caelum.vraptorlight.core;

import javax.servlet.http.HttpServletRequest;

public class VRaptorRequest {

	private final HttpServletRequest request;

	public VRaptorRequest(HttpServletRequest req) {
		this.request = req;
	}

	public String param(String name) {
		return this.request.getParameter(name);
	}

	public String pathParam(String name) {
		return (String) request.getAttribute(name);
	}

	public void addAttribute(String key, String value) {
		request.setAttribute(key, value);
	}

}
