package nhat.pa._cntt.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import nhat.pa._cntt.Entity.DoctorsEntity;
import nhat.pa._cntt.Repository.DoctorRepository;

@Controller
@RequestMapping("/phong-kham-vi-suc-khoe")
public class DoctorController {
	@Autowired
    private DoctorRepository doctorRepository;

    @GetMapping("/bac-si")
    public String bacsi(Model model) {
        List<DoctorsEntity> doctors = doctorRepository.findDoctorsWithDoctorRoleState();
        model.addAttribute("doctors", doctors);
        return "bacSi";
    }
    
    @GetMapping("/bac-si/{id}")
    public String doctorDetail(@PathVariable("id") Integer id, Model model) {
        DoctorsEntity doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid doctor ID: " + id));
        model.addAttribute("doctor", doctor);
        return "chitietBS";
    }
}
