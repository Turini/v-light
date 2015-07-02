package br.com.caelum.vraptorlight.core;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import br.com.caelum.vraptorlight.http.HttpMethod;
import br.com.caelum.vraptorlight.output.*;
import br.com.caelum.vraptorlight.processor.*;

public abstract class Application {

	private final List<UriPattern> mappedUris = new ArrayList<>();
	
	protected abstract void setup();
	
	public void process(String path, HttpMethod method, VRaptorRequest req, VRaptorResponse res) {
		List<UriPattern> mappedUris = searchMappedUrisFor(path);
		if(mappedUris.isEmpty()) {
			throw new RuntimeException("404");
		}
		Optional<UriPattern> uri = searchAllowedUrisFor(method, mappedUris);
		if(uri.isPresent()) {
			uri.get().getAction().process(req, res);
		} else {
			throw new RuntimeException("This path isn't allowed for method " + method);
		}
	}

	private Optional<UriPattern> searchAllowedUrisFor(HttpMethod httpMethod,
			List<UriPattern> mappedUris) {
		//TODO: check for conflicts, ambiguous route, etc 
		return mappedUris.stream().filter(uri -> uri.allows(httpMethod)).findFirst();
	}

	private List<UriPattern> searchMappedUrisFor(String path) {
		return mappedUris.stream().filter(p -> p.answers(path)).collect(toList());
	}
	
	private void get(String uriPattern, Action processor) {
		mappedUris.add(new FixedUri(HttpMethod.GET, uriPattern, processor));
	}
	
	protected void get(String uriPattern, FixedOutput output) {
		get(uriPattern, new FixedOutputProcessor(output));
	}

	//TODO: better name, please!
	protected void get(String uriPattern, RequestResponseOutput output) {
		get(uriPattern, new RequestResponseProcessor(output));
	}
	
	//TODO: better name, please!
	protected void get(String uriPattern, CustomOutput output) {
		get(uriPattern, new CustomOutputProcessor(output));
	}
	
	private void post(String uriPattern, Action processor) {
		mappedUris.add(new FixedUri(HttpMethod.POST, uriPattern, processor));
	}
	
	protected void post(String uriPattern, FixedOutput output) {
		post(uriPattern, new FixedOutputProcessor(output)); 
	}
	
	//TODO: better name, please!
	protected void post(String uriPattern, RequestResponseOutput output) {
		post(uriPattern, new RequestResponseProcessor(output));
	}
	
	//TODO: better name, please!
	protected void post(String uriPattern, CustomOutput output) {
		post(uriPattern, new CustomOutputProcessor(output));
	}
}