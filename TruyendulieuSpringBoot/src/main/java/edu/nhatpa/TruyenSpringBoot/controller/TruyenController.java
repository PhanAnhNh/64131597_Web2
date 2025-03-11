package edu.nhatpa.TruyenSpringBoot.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
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
	
	@GetMapping("/truyendsObject")
	public String getStudents(ModelMap model) {
		List<Student> studentlist = new ArrayList<>();
		studentlist.add(new Student("64131597", "Nam", 2004, "Phan Anh Nhat"));
		studentlist.add(new Student("64131123", "Nu", 2004, "Pham Thai Chau Long"));
		studentlist.add(new Student("64131345", "Nam", 2004, "Nguyen Anh Hung"));
		studentlist.add(new Student("64131679", "Nu", 2003, "Phan Anh Duy"));
		
		model.addAttribute("student", studentlist);
		return "viewobject";
	}
}
