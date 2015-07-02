package br.com.caelum.vraptorlight.http;

public enum HttpMethod {
	
	GET, POST;

	public static HttpMethod of(String method) {
		try {
			return valueOf(method.toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Unknown HTTP Method " + method, e);
		}
	}
}
