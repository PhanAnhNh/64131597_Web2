package edu.nhatpa.TruyenSpringBoot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import model.Student;

@Controller
public class TruyenController {
	@GetMapping("/student")
	public String getStudent(Model model) {
		Student student = new Student("64131597", "Nam", 2004, "Phan Anh Nhất");
		model.addAttribute("student", student);
		return "index";
	}
}
