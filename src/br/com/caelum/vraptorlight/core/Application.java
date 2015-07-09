package br.com.caelum.vraptorlight.core;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import br.com.caelum.vraptorlight.http.HttpMethod;
import br.com.caelum.vraptorlight.output.CustomOutput;
import br.com.caelum.vraptorlight.output.FixedOutput;
import br.com.caelum.vraptorlight.output.RequestResponseOutput;
import br.com.caelum.vraptorlight.processor.CustomOutputProcessor;
import br.com.caelum.vraptorlight.processor.FixedOutputProcessor;
import br.com.caelum.vraptorlight.processor.RequestResponseProcessor;

public abstract class Application {

	private final List<UriPattern> mappedUris = new ArrayList<>();
	private final UriFactory uriFactory = new UriFactory();
	
	protected abstract void setup();
	
	public void process(String path, HttpMethod method, VRaptorRequest req, VRaptorResponse res) {
		//TODO: merge this method with searchAllowedUrisFor?
		List<UriPattern> mappedUris = searchMappedUrisFor(path);
		if(mappedUris.isEmpty()) {
			throw new RuntimeException("404");
		}
		Optional<UriPattern> uri = searchAllowedUrisFor(method, mappedUris);
		if(uri.isPresent()) {
			UriPattern uriPattern = uri.get();
			if (isParameterizedUri(uriPattern)) {
				addPathParameters(path, req, uriPattern);
			}
			uriPattern.getAction().process(req, res);
		} else {
			throw new RuntimeException("This path isn't allowed for method " + method);
		}
	}

	private void addPathParameters(String path, VRaptorRequest req, UriPattern uri) {
		ParameterizedUri paramUri = (ParameterizedUri) uri;
		paramUri.getPathParameters(path).entrySet().stream().forEach(e -> {
			req.addAttribute(e.getKey(), e.getValue());
		});
	}

	private boolean isParameterizedUri(UriPattern uriPattern) {
		return ParameterizedUri.class.isAssignableFrom(uriPattern.getClass());
	}

	private Optional<UriPattern> searchAllowedUrisFor(HttpMethod httpMethod,
			List<UriPattern> mappedUris) {
		//TODO: check for conflicts, ambiguous route, etc 
		return mappedUris.stream().filter(uri -> uri.allows(httpMethod)).findFirst();
	}

	private List<UriPattern> searchMappedUrisFor(String path) {
		//TODO: check for conflicts, ambiguous route, etc 
		return mappedUris.stream().filter(p -> p.answers(path)).collect(toList());
	}
	
	private void get(String uriPattern, Action processor) {
		mappedUris.add(uriFactory.uriFor(HttpMethod.GET, uriPattern, processor));
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
		mappedUris.add(uriFactory.uriFor(HttpMethod.POST, uriPattern, processor));
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