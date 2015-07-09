package br.com.caelum.vraptorlight.core;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

public class VRaptorResponse {

	private final HttpServletResponse response;
	private final String contextPath; //FIXME

	public VRaptorResponse(HttpServletResponse res, String contextPath) {
		this.response = res;
		this.contextPath = contextPath;
	}

	public void write(String content) {
		try {
			this.response.getWriter().print(content);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void header(String key, String value) {
		this.response.addHeader(key, value);
	}

	public void redirect(String path) {
		try {
			boolean root = "/".equals(contextPath);
			path = root ? path : contextPath + path;
			this.response.sendRedirect(path);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}