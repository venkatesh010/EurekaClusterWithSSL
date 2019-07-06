package com.example.Discssl;

import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Profile("zone2")
public class Zone2 {

	@GetMapping("/hello")
	public String hello(){
		return "Discovery2";
	}

}
