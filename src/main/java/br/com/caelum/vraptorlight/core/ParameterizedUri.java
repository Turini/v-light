package br.com.caelum.vraptorlight.core;

import static java.util.regex.Pattern.compile;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.caelum.vraptorlight.http.HttpMethod;

public class ParameterizedUri implements UriPattern {

	private final HttpMethod method;
	private final String uri;
	private final Action processor;

	public ParameterizedUri(HttpMethod method, String uri, Action processor) {
		this.method = method;
		this.uri = uri;
		this.processor = processor;
	}

	@Override
	public boolean answers(String path) {
		if (!hasSameLenght(uri, path)) return false;
		
		String[] splitedUri = uri.split("/");
		String[] splitedPath = path.split("/");
		
		for (int i = 0; i < splitedUri.length; i++) {
			String a = splitedUri[i];
			String b = splitedPath[i];
			if (compile("\\{(.*?)\\}").matcher(a).find()) {
				continue;
			}
			if (!a.equals(b)) return false;
		}
		return true;
	}
	
	private boolean hasSameLenght(String uri, String path) {
		return uri.split("/").length == path.split("/").length;
	}

	@Override
	public boolean allows(HttpMethod httpMethod) {
		return this.method.equals(httpMethod);
	}

	@Override
	public Action getAction() {
		return processor;
	}

	@Override
	public String getUri() {
		return uri;
	}

	public Map<String, String> getPathParameters(String path) {
		Pattern pattern = Pattern.compile("\\{(.*?)\\}");

		String[] uris = uri.split("/");
		
		Map<String,String> map = new HashMap<>();
		
		for (int i = 0; i < uris.length; i++){
			Matcher matcher = pattern.matcher(uris[i]);
			if (matcher.find()) {
				map.put(matcher.group(1), path.split("/")[i]);
			}
		}
		return map;
	}
}