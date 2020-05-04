package in.sabnar.gateway.controller;

import in.sabnar.gateway.dto.AdditionalInfo;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/resource")
public class ResourceController {
	
	@GetMapping
	public Map<String, String> getResource(HttpServletRequest request,
										   HttpServletResponse response) throws Exception {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		AdditionalInfo extraInfo = (AdditionalInfo)auth.getPrincipal();

		System.out.println("Additional Stored Data --->"+ printAdditionalData(extraInfo.getData()));
		System.out.println("----->"+ request.getSession().getAttribute("age"));
		Map<String, String> resource = new HashMap<String, String>();
		resource.put("resource", "here is some resource");
		return resource;
	}

	private String printAdditionalData(Map<String, Object> data) {
		data.forEach((k,v) ->{
			System.out.println(k+"<-->"+v);
		});
		return "-------------------------------";
	}

	@GetMapping("/data")
	@ResponseStatus(HttpStatus.OK)
	public String getData(HttpSession session) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		AdditionalInfo extraInfo = (AdditionalInfo)auth.getPrincipal();
		Map addData = extraInfo.getData();
		addData.put("Gender", "Male");
		session.setAttribute("age", "35");
		return "Hello Data";
	}

	@GetMapping("/logout")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void logout(HttpSession session) {
		session.invalidate();
	}
}
