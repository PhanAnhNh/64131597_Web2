package edu.nhapa.OnTap.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TongHopController {
	@GetMapping("/")
    public String home() {
        return "index"; // Trả về file index.html
    }
}
