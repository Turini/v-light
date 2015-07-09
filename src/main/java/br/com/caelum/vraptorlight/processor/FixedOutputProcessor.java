package br.com.caelum.vraptorlight.processor;

import br.com.caelum.vraptorlight.core.Action;
import br.com.caelum.vraptorlight.core.VRaptorRequest;
import br.com.caelum.vraptorlight.core.VRaptorResponse;
import br.com.caelum.vraptorlight.output.FixedOutput;

public class FixedOutputProcessor implements Action {

	private final FixedOutput output;

	public FixedOutputProcessor(FixedOutput fixedOutput) {
		this.output = fixedOutput;
	}

	@Override
	public void process(VRaptorRequest req, VRaptorResponse res) {
		try {
			String response = output.method();
			res.write(response);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}