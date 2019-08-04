package com.example.Dumm;


import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Profile("zone2")
public class Zone2 {

	@GetMapping(value = "/hello", produces = MediaType.APPLICATION_JSON_VALUE)
	public Payload hello(){
		return new Payload("Hello Dummy");
	}

	@GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	public Payload common(){
		return new Payload("Dummy");
	}

}


