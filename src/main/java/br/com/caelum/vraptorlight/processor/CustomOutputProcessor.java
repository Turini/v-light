package br.com.caelum.vraptorlight.processor;

import br.com.caelum.vraptorlight.core.Action;
import br.com.caelum.vraptorlight.core.VRaptorRequest;
import br.com.caelum.vraptorlight.core.VRaptorResponse;
import br.com.caelum.vraptorlight.output.CustomOutput;

public class CustomOutputProcessor implements Action {

	private final CustomOutput fixedOutput;

	public CustomOutputProcessor(CustomOutput fixedOutput) {
		this.fixedOutput = fixedOutput;
	}

	@Override
	public void process(VRaptorRequest req, VRaptorResponse res) {
		try {
			fixedOutput.method(req, res);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}		
	}
}