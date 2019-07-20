package com.example.gateway;

import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Profile("zone1")
public class Zone1 {

	@GetMapping("/hello")
	public String hello(){
		return "Gateway";
	}

}
