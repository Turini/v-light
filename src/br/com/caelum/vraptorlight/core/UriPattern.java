package br.com.caelum.vraptorlight.core;

import br.com.caelum.vraptorlight.http.HttpMethod;

public interface UriPattern {

	boolean answers(String path);
	boolean allows(HttpMethod httpMethod);
	Action getAction();
	public abstract String getUri();
}
