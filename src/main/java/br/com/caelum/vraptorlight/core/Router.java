package br.com.caelum.vraptorlight.core;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;

import br.com.caelum.vraptorlight.http.HttpMethod;

public class Router {
	
	private final List<UriPattern> mappedUris = new ArrayList<>();

	public UriPattern searchMappedUrisFor(String path, HttpMethod method) {
		List<UriPattern> uris = mappedUris.stream().filter(p -> p.answers(path)).collect(toList());
		if(uris.isEmpty()) {
			throw new RuntimeException("404");
		} 
		if(uris.stream().filter(u -> u.allows(method)).count() > 1){
			String routes = uris.stream().map(UriPattern::getUri).collect(joining(","));
			throw new RuntimeException("Found ambiguity between routes: " + routes);
		}
		return searchAllowedUrisFor(method, uris);
	}

	private UriPattern searchAllowedUrisFor(HttpMethod method, List<UriPattern> uris) {
		return uris.stream().filter(uri -> uri.allows(method)).findFirst().orElseThrow(() -> {
			return new RuntimeException("This path isn't allowed for method " + method);
		});
	}
	
	public void add(UriPattern uriFor) {
		mappedUris.add(uriFor);
	}
}