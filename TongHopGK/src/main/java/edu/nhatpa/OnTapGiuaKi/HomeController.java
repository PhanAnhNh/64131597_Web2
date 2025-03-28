package edu.nhatpa.OnTapGiuaKi;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {
	private final List<Student> students = new ArrayList<>();
	
	@GetMapping("/")
    public String home() {
        return "index"; // Trả về file index.html
    }
	
	@GetMapping("/about")
    public String about() {
        return "about"; // Trả về file about.html
    }
	
	@GetMapping("/list")
    public String list(Model model) {
		model.addAttribute("students", students);
        return "list"; // Trả về file about.html
    }
	
	@GetMapping("/add")
    public String add() {
        return "add"; // Trả về file about.html
    }
	
	@PostMapping("/add")
    public String addStudent(
            @RequestParam String name,
            @RequestParam String studentId,
            @RequestParam double score) {
        
        students.add(new Student(name, studentId, score));
        return "redirect:/list"; // Sau khi thêm, quay lại danh sách
    }
	
	@GetMapping("/delete")
	public String deleteStudent(@RequestParam String studentId) {
	    students.removeIf(student -> student.getStudentId().equals(studentId));
	    return "redirect:/list";
	}
	
	@GetMapping("/edit")
	public String editStudent(@RequestParam String studentId, Model model) {
	    Student student = students.stream()
	        .filter(s -> s.getStudentId().equals(studentId))
	        .findFirst()
	        .orElse(null);
	    model.addAttribute("student", student);
	    return "edit";
	}
	
	@PostMapping("/update")
	public String updateStudent(
	        @RequestParam String studentId,
	        @RequestParam String name,
	        @RequestParam double score) {

	    for (Student student : students) {
	        if (student.getStudentId().equals(studentId)) {
	            student.setName(name);
	            student.setScore(score);
	            break;
	        }
	    }
	    return "redirect:/list";
	}


}
