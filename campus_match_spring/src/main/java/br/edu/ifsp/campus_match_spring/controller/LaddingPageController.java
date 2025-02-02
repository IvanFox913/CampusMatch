package br.edu.ifsp.campus_match_spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class LaddingPageController {

	@GetMapping("index")
	public String landingPage(Model model) {
		return "redirect:/auth/login";
	}
	
	@GetMapping("/")
	public String landingPage2(Model model) {
		return "redirect:/auth/login";
	}
	
}
