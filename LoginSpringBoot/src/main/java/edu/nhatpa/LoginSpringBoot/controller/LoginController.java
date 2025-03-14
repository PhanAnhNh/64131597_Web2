package edu.nhatpa.LoginSpringBoot.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import jakarta.servlet.http.HttpServletRequest;


@Controller
public class LoginController {
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String showLoginForm() {
	    return "login"; // Hiển thị trang login.html
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login( HttpServletRequest request) {
		
		String id = request.getParameter("id");
		String password = request.getParameter("password");
		
		//kiểm tra id và pass
		if("admin".equals(id) && "123456".equals(password)) {
			return "aboutme";
		} else {
			return "login";
		}
	}
}
