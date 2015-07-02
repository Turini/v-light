package br.com.caelum.vraptorlight.core;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.caelum.vraptorlight.http.HttpMethod;
import br.com.caelum.vraptorlight.sample.HelloWorldApplication;

@WebFilter("/*")
public class VRaptorLightFilter implements Filter {

	private Application app;

	public void init(FilterConfig fConfig) throws ServletException {
		// TODO instnaciar de maneira magica
		this.app = new HelloWorldApplication();
		app.setup();
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		if (app == null) {
			throw new IllegalStateException("App did not load properly");
		}
		HttpServletRequest originalRequest = (HttpServletRequest) request;
		HttpServletResponse originalResponse = (HttpServletResponse) response;
		
		String contextPath = originalRequest.getContextPath();
		VRaptorRequest req = new VRaptorRequest(originalRequest);
		VRaptorResponse res = new VRaptorResponse(originalResponse, contextPath);
		
		String requestedPath = getRequestedURI(originalRequest, contextPath);
		HttpMethod httpMethod = HttpMethod.of(originalRequest.getMethod());
		app.process(requestedPath, httpMethod, req, res);
	}

	private String getRequestedURI(HttpServletRequest req, String contextPath) {
		if ("/".equals(contextPath)) return req.getRequestURI();
		return req.getRequestURI().substring(contextPath.length());
	}

	public void destroy() {
		this.app = null;
	}
}
