package com.projects.gallery.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AboutLoginController {

	
	@GetMapping("/about")
	public String aboutPage() {
		return "about";
	}

	@GetMapping("/login")
	public String loginPage() {
		return "loginPage";
	}
	
}
