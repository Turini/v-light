package br.com.caelum.vraptorlight.processor;

import br.com.caelum.vraptorlight.core.Action;
import br.com.caelum.vraptorlight.core.VRaptorRequest;
import br.com.caelum.vraptorlight.core.VRaptorResponse;
import br.com.caelum.vraptorlight.output.RequestResponseOutput;

public class RequestResponseProcessor implements Action {

	private final RequestResponseOutput fixedOutput;

	public RequestResponseProcessor(RequestResponseOutput fixedOutput) {
		this.fixedOutput = fixedOutput;
	}

	@Override
	public void process(VRaptorRequest req, VRaptorResponse res) {
		try {
			String response = fixedOutput.method(req, res);
			res.write(response);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}