package br.com.caelum.vraptorlight.core;

import static java.util.regex.Pattern.compile;
import br.com.caelum.vraptorlight.http.HttpMethod;

public class UriFactory {

	public UriPattern uriFor(HttpMethod method, String pattern, Action processor) {
		if (compile("\\{(.*?)\\}").matcher(pattern).find()){
			return new ParameterizedUri(method, pattern, processor);
		}
		return new FixedUri(method, pattern, processor);
	}

}
