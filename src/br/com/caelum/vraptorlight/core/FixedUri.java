package br.com.caelum.vraptorlight.core;

import br.com.caelum.vraptorlight.http.HttpMethod;

public class FixedUri implements UriPattern {

	private final String uriPattern;
	private final Action action;
	private final HttpMethod method;

	public FixedUri(HttpMethod method, String uriPattern, Action action) {
		this.method = method;
		this.uriPattern = uriPattern;
		this.action = action;
	}

	@Override
	public boolean answers(String path) {
		//TODO refactor to an component
		//TODO regex matcher
		return uriPattern.equals(path);
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
