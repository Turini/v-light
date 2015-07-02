package br.com.caelum.vraptorlight.core;

@FunctionalInterface
public interface Action {

	void process(VRaptorRequest req, VRaptorResponse res);
}
