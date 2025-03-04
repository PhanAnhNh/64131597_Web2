package edu.nhatpa.ChaoSpringBoot;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebController {

	@GetMapping("/")
	public String index(ModelMap model) {
		model.addAttribute("tb", "Hello");
		return "index";
	}
}
