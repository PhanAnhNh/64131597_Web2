package jp.ivs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Controller
@EnableWebMvc
public class WelcomeController {
	@RequestMapping("/welcome.html")
	public String welcome() {
		return "viewWelcome";
	}
}
