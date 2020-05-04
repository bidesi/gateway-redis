package in.sabnar.gateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/test")
public class TestController {

	@GetMapping("/hello")
	public String sayHello() {
		return "Hello World";
	}

	/**
	 * Simple Session controller which will return session ID backed by Spring
	 * Session API
	 * 
	 * @param session
	 * @return session ID
	 */
	@GetMapping("/uid")
	String uid(HttpSession session) {
		return session.getId();
	}

}
