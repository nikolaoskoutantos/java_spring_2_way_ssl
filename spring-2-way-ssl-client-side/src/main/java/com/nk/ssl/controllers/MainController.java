package com.nk.ssl.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class MainController {
	@Value("${endpoint.ms-service}")
	private String msEndpoint;
	
	@GetMapping("/data")
	public String getData() {
		System.err.println("Returning Data from nt-gateway");
		return "Hi from nt-gateway";
	}
	
	
	@GetMapping("/server-data")
	public String getServerData() {
		
		
		
	try {
		RestTemplate restTemp = new RestTemplate();
		return restTemp.getForObject(new URI(msEndpoint), String.class);
		} catch(Exception ex) {
		System.err.println("An exception have occured" + ex);
		ex.printStackTrace();}
		return msEndpoint;
}
	
}
