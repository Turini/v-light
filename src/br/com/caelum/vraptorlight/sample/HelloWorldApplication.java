package br.com.caelum.vraptorlight.sample;

import br.com.caelum.vraptorlight.core.Application;


public class HelloWorldApplication extends Application {
	
	@Override
	public void setup() {
		
		get("/hello", () -> "Hello World");
		
		get("/wellcome", (req, res) -> {
			return "Welcome "+ req.param("name");
		});
		
		get("/writing", (req, res) -> {
			String name = req.param("name");
			res.write("Welcome "+ name);
		});
		
		get("/header", (req, res) -> {
			res.header("token", "1ASh123huA90wehas");
			return "checkout your header";
		});
		
		get("/redirecting", (req, res) -> {
			res.redirect("/hello");
		});
		
		post("/postMethod", () -> "Hello World");
		
//		get("/path/param/{id}", (req, res) -> {
//			return "Path param is " + req.pathParam("id");
//		});
	
	}
}