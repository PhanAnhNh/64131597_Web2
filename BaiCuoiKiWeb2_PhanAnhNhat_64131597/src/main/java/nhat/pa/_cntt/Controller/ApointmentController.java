package nhat.pa._cntt.Controller;

import nhat.pa._cntt.Entity.AppointmentRequestDTO;
import nhat.pa._cntt.Entity.DoctorsEntity;
import nhat.pa._cntt.Service.AppointmentService;
import nhat.pa._cntt.Service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/phong-kham-vi-suc-khoe")
public class ApointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private DoctorService doctorService;

    @GetMapping("/dat-lich-kham")
    public String showAppointmentForm(Model model) {
        List<DoctorsEntity> doctors = doctorService.findActiveDoctors();
        model.addAttribute("doctors", doctors);
        model.addAttribute("appointmentRequest", new AppointmentRequestDTO());
        return "datlich";
    }

    @PostMapping("/dat-lich-kham")
    public String bookAppointment(@Validated @ModelAttribute("appointmentRequest") AppointmentRequestDTO request,
                                 BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", "Vui lòng điền đầy đủ thông tin hợp lệ: " +
                    result.getAllErrors().get(0).getDefaultMessage());
            return "redirect:/phong-kham-vi-suc-khoe/dat-lich-kham";
        }

        try {
            appointmentService.bookAppointment(
                    request.getFullName(),
                    request.getGender(),
                    request.getPhone(),
                    request.getEmail(),
                    request.getAddress(),
                    request.getDoctorId(),
                    request.getAppointmentDate()
            );
            redirectAttributes.addFlashAttribute("message", "Đặt lịch khám thành công! Vui lòng chờ xác nhận.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/phong-kham-vi-suc-khoe/dat-lich-kham";
    }
}