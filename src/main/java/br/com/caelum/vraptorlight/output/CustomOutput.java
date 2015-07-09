package br.com.caelum.vraptorlight.output;

import br.com.caelum.vraptorlight.core.VRaptorRequest;
import br.com.caelum.vraptorlight.core.VRaptorResponse;

@FunctionalInterface
public interface CustomOutput {

	void method(VRaptorRequest req, VRaptorResponse res);
}