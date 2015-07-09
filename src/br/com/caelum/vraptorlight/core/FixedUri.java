package br.com.caelum.vraptorlight.core;

import br.com.caelum.vraptorlight.http.HttpMethod;

public class FixedUri implements UriPattern {

	private final String uri;
	private final Action action;
	private final HttpMethod method;

	public FixedUri(HttpMethod method, String uriPattern, Action action) {
		this.method = method;
		this.uri = uriPattern;
		this.action = action;
	}

	@Override
	public boolean answers(String path) {
		return uri.equals(path);
	}
	
	@Override
	public String getUri() {
		return uri;
	}
	
	@Override
	public boolean allows(HttpMethod httpMethod) {
		return this.method.equals(httpMethod);
	}
	
	@Override
	public Action getAction() {
		return action;
	}
}
