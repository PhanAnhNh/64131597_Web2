package edu.nhatpa.BMISpringBoot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import model.BmiModel;


@Controller
public class BMISpringBootController {
	@GetMapping("/")
    public String showForm(Model model) {
        model.addAttribute("bmiModel", new BmiModel());
        return "index";
    }

    @PostMapping("/calculate")
    public String calculateBmi(@ModelAttribute BmiModel bmiModel, Model model) {
        bmiModel = new BmiModel(bmiModel.getHeight(), bmiModel.getWeight());
        model.addAttribute("bmiModel", bmiModel);
        return "result";
    }
}
