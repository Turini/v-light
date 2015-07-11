package br.com.caelum.vraptorlight.core;

import java.util.function.Function;

import br.com.caelum.vraptorlight.http.HttpMethod;
import br.com.caelum.vraptorlight.output.CustomOutput;
import br.com.caelum.vraptorlight.output.FixedOutput;
import br.com.caelum.vraptorlight.output.RequestResponseOutput;
import br.com.caelum.vraptorlight.processor.CustomOutputProcessor;
import br.com.caelum.vraptorlight.processor.FixedOutputProcessor;
import br.com.caelum.vraptorlight.processor.RequestResponseProcessor;
import br.com.caelum.vraptorlight.sample.Analytics;
import br.com.caelum.vraptorlight.sample.AnalyzeController;

public abstract class Application {

	private final UriFactory uriFactory = new UriFactory();
	private final Router router = new Router();
	
	protected abstract void setup();
	
	public void process(String path, HttpMethod method, VRaptorRequest req, VRaptorResponse res) {
		UriPattern uriPattern = router.searchMappedUrisFor(path, method);
		if (isParameterizedUri(uriPattern)) {
			addPathParameters(path, req, uriPattern);
		}
		uriPattern.getAction().process(req, res);
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

	private void get(String uriPattern, Action processor) {
		router.add(uriFactory.uriFor(HttpMethod.GET, uriPattern, processor));
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
		router.add(uriFactory.uriFor(HttpMethod.POST, uriPattern, processor));
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

	
	
	
	
	
	
	//TODO daqui pra frente é teste
	
	protected void get(String uriPattern, Function<AnalyzeController, String> func, Analytics analytics) {
		get(uriPattern, new FunctionProcessor(func, analytics));
	}
//	protected void get(String uriPattern, Function<ProdutoController, String> func) {
//		get(uriPattern, new FunctionProcessor(func));
//	}
}