package br.com.caelum.vraptorlight.output;

import br.com.caelum.vraptorlight.core.VRaptorRequest;
import br.com.caelum.vraptorlight.core.VRaptorResponse;

@FunctionalInterface
public interface RequestResponseOutput {

	String method(VRaptorRequest req, VRaptorResponse res);
}
