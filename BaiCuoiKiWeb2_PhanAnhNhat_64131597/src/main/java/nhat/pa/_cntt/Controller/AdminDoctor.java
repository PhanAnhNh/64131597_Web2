package nhat.pa._cntt.Controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;
import nhat.pa._cntt.Entity.DoctorsEntity;
import nhat.pa._cntt.Entity.UsersEntity;
import nhat.pa._cntt.Service.AppointmentService;
import nhat.pa._cntt.Service.DoctorService;
import nhat.pa._cntt.Service.PatientsService;
import nhat.pa._cntt.Service.UsersService;

@Controller
@RequestMapping("/bac-si-phong-kham")
public class AdminDoctor {

	@Autowired
	private DoctorService doctorService;
	
	@Autowired
	private UsersService usersService;
	
	@Autowired
    private PatientsService patientService;

    @Autowired
    private AppointmentService appointmentService;

    private static final String UPLOAD_DIR = "src/main/resources/static/images/";
    
	@GetMapping("/quan-ly-bac-si")
	public String QLBacSi(Model model, HttpSession session) {
		
		List<DoctorsEntity> doctors = doctorService.findDoctorsWithDoctorRole();
		model.addAttribute("doctors", doctors);
		return "quanlybacsi";
	}
	
	@GetMapping("/chi-tiet-bac-si")
	public String viewDoctorDetails(@RequestParam("id") Integer doctorId, Model model, HttpSession session) {
		
		DoctorsEntity doctor = doctorService.findById(doctorId);
		model.addAttribute("doctor", doctor);
		return "ChitietQLBS";
	}
	
	@GetMapping("/sua-bac-si")
	public String showEditDoctorForm(@RequestParam("id") Integer doctorId, Model model, HttpSession session) {
	    DoctorsEntity doctor = doctorService.findById(doctorId);
	    model.addAttribute("doctor", doctor);
	    return "suaBS";
	}

	@PostMapping("/sua-bac-si")
    public String updateDoctor(
            @RequestParam("id") Integer doctorId,
            @RequestParam("fullname") String fullname,
            @RequestParam("department") String department,
            @RequestParam("phone") String phone,
            @RequestParam("email") String email,
            @RequestParam("workinghours") String workinghours,
            @RequestParam(value = "address", required = false) String address,
            @RequestParam(value = "qualifications", required = false) String qualifications,
            @RequestParam("gender") String gender,
            @RequestParam("status") String status,
            @RequestParam(value = "profileImage", required = false) MultipartFile profileImage,
            Model model,
            HttpSession session) {
        try {
            DoctorsEntity doctor = doctorService.findById(doctorId);
            doctor.setFullname(fullname);
            doctor.setDepartment(department);
            doctor.setPhone(phone);
            doctor.setEmail(email);
            doctor.setWorkinghours(workinghours);
            doctor.setAddress(address);
            doctor.setQualifications(qualifications);
            doctor.setGender(gender);
            doctor.setStatus(DoctorsEntity.Status.valueOf(status));

            // Handle file upload
            if (profileImage != null && !profileImage.isEmpty()) {
                try {
                    // Validate file type
                    String contentType = profileImage.getContentType();
                    if (contentType == null || !contentType.startsWith("image/")) {
                        model.addAttribute("error", "Vui lòng chọn file hình ảnh (jpg, png, gif).");
                        model.addAttribute("doctor", doctor);
                        return "suaBS";
                    }

                    // Create uploads directory if it doesn't exist
                    Path uploadPath = Paths.get(UPLOAD_DIR);
                    if (!Files.exists(uploadPath)) {
                        Files.createDirectories(uploadPath);
                    }

                    // Generate unique filename
                    String originalFilename = profileImage.getOriginalFilename();
                    String fileExtension = originalFilename != null ? originalFilename.substring(originalFilename.lastIndexOf(".")) : ".jpg";
                    String uniqueFilename = UUID.randomUUID().toString() + fileExtension;
                    Path filePath = uploadPath.resolve(uniqueFilename);

                    // Save file to the server
                    Files.write(filePath, profileImage.getBytes());

                    // Delete old image if it exists
                    if (doctor.getProfileImage() != null && !doctor.getProfileImage().isEmpty()) {
                        Path oldFilePath = Paths.get(UPLOAD_DIR, doctor.getProfileImage().substring("/images/".length()));
                        Files.deleteIfExists(oldFilePath);
                    }

                    // Set the new file path in the entity
                    doctor.setProfileImage("/images/" + uniqueFilename);
                } catch (IOException e) {
                    model.addAttribute("error", "Lỗi khi tải lên ảnh: " + e.getMessage());
                    model.addAttribute("doctor", doctor);
                    return "suaBS";
                }
            }

            doctorService.saveDoctor(doctor);
            return "redirect:/bac-si-phong-kham/quan-ly-bac-si?success=true";
        } catch (Exception e) {
            model.addAttribute("error", "Lỗi khi cập nhật bác sĩ: " + e.getMessage());
            model.addAttribute("doctor", doctorService.findById(doctorId));
            return "suaBS";
        }
    }
	
	//Xóa bác sĩ
	
	@GetMapping("/xoa-bac-si")
    public String deleteDoctor(@RequestParam("id") Integer doctorId, HttpSession session) {
        try {
            DoctorsEntity doctor = doctorService.findById(doctorId);
            if (doctor.getProfileImage() != null && !doctor.getProfileImage().isEmpty()) {
                Path filePath = Paths.get(UPLOAD_DIR, doctor.getProfileImage().substring("/images/".length()));
                Files.deleteIfExists(filePath);
            }
            doctorService.deleteDoctor(doctorId);
            return "redirect:/bac-si-phong-kham/quan-ly-bac-si";
        } catch (Exception e) {
            return "redirect:/bac-si-phong-kham/quan-ly-bac-si?error=" + e.getMessage();
        }
    }
	//thống kê
	@GetMapping("/thong-ke")
    public String showStatistics(Model model, HttpSession session) {

        LocalDate today = LocalDate.now();
        long appointmentsToday = appointmentService.countAppointmentsToday(today);
        long totalPatients = patientService.countPatients();
        long totalDoctors = doctorService.countDoctors();
        long totalDepartments = doctorService.countDepartments();
        Map<String, Long> weeklyAppointments = appointmentService.getWeeklyAppointments(today);
        List<Map<String, Object>> topDoctors = appointmentService.getTopDoctorsByAppointments();

        model.addAttribute("appointmentsToday", appointmentsToday);
        model.addAttribute("totalPatients", totalPatients);
        model.addAttribute("totalDoctors", totalDoctors);
        model.addAttribute("totalDepartments", totalDepartments);
        model.addAttribute("weeklyAppointments", weeklyAppointments);
        model.addAttribute("topDoctors", topDoctors);

        return "thongkeBenhNhan";
    }
}
