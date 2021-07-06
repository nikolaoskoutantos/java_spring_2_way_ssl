package com.nk.ssl.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class MainController {
	
	
	@GetMapping("/")
	public String homepage() {
		return "Hello from Homepage";
	}
	
	@GetMapping("/data")
	public String getData() {
		
		
		return "OK";
}
	
	
	
}
