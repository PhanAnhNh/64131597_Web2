package nhat.pa._cntt.Controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;
import nhat.pa._cntt.Entity.AppointmentEntity;
import nhat.pa._cntt.Entity.DoctorsEntity;
import nhat.pa._cntt.Entity.PatientsEntity;
import nhat.pa._cntt.Entity.UsersEntity;
import nhat.pa._cntt.Repository.AppointmentReponsitory;
import nhat.pa._cntt.Service.AppointmentService;
import nhat.pa._cntt.Service.DoctorService;
import nhat.pa._cntt.Service.PatientsService;
import nhat.pa._cntt.Service.UsersService;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
@Controller
@RequestMapping("/bac-si-phong-kham")
public class AdminController {
	
	@Autowired
    private AppointmentReponsitory appointmentRepository;
	@Autowired
    private UsersService userService;
	
	@Autowired
    private PatientsService patientsService;
	
	@Autowired
    private DoctorService doctorService;
	
	@Autowired
    private AppointmentService appointmentService;
	
	@Autowired
    private BCryptPasswordEncoder passwordEncoder;
	
	// Directory to store uploaded images
		private static final String UPLOAD_DIR = "src/main/resources/static/images/";

	@GetMapping("/admin")
    public String admin(Model model, Authentication authentication) {
        if (authentication != null) {
            model.addAttribute("username", authentication.getName());
        }
        return "admin";
    }
	
	@GetMapping("/quan-ly-lich-hen")
    public String manageAppointments(Model model, HttpSession session) {
		List<AppointmentEntity> appointments = appointmentRepository.findAll();
        model.addAttribute("appointments", appointments);
        return "quanlylichhen";
    }
	
	@GetMapping("/dang-nhap")
    public String loginPage(Model model, @RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "logout", required = false) String logout) {
        if (error != null) {
            model.addAttribute("error", "Email hoặc mật khẩu không đúng.");
        }
        if (logout != null) {
            model.addAttribute("message", "Bạn đã đăng xuất thành công.");
        }
        return "dangnhap";
    }
	

	@GetMapping("/dang-ky")
    public String registerPage(Model model) {
        model.addAttribute("user", new UsersEntity());
        model.addAttribute("doctor", new DoctorsEntity());
        return "dangky";
    }
	
	@PostMapping("/dang-ky")
    public String register(@ModelAttribute UsersEntity user, @RequestParam String fullname,
                           @RequestParam String phone, Model model) {
        try {
            user.setRole(UsersEntity.Role.doctor);
            UsersEntity savedUser = userService.registerUser(user);
            doctorService.registerDoctor(savedUser, fullname, phone, user.getEmail());
            return "redirect:/bac-si-phong-kham/quan-ly-bac-si?registered=true";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("user", user);
            model.addAttribute("doctor", new DoctorsEntity());
            return "dangky";
        }
    }
	
	@GetMapping("/chitiet-benhnhan")
    public String viewPatientDetails(@RequestParam("id") Integer patientId, Model model, HttpSession session) {
        
        List<AppointmentEntity> appointments = appointmentRepository.findByPatientPatientid(patientId);
        if (appointments != null && !appointments.isEmpty()) {
            // Select the most recent appointment
            AppointmentEntity latestAppointment = appointments.stream()
                .sorted((a1, a2) -> {
                    if (a1.getAppointmentdate().equals(a2.getAppointmentdate())) {
                        return a2.getAppointmenttime().compareTo(a1.getAppointmenttime());
                    }
                    return a2.getAppointmentdate().compareTo(a1.getAppointmentdate());
                })
                .findFirst()
                .orElse(null);
            model.addAttribute("appointment", latestAppointment);
        } else {
            model.addAttribute("error", "Không tìm thấy thông tin bệnh nhân.");
        }
        return "chitietBN";
    }
	
	@PostMapping("/xac-nhan-kham")
    public String confirmAppointment(@RequestParam("appointmentId") Integer appointmentId, HttpSession session) {
        
        AppointmentEntity appointment = appointmentRepository.findById(appointmentId).orElse(null);
        if (appointment != null) {
            appointment.setStatus(AppointmentEntity.Status.Đã_xác_nhận);
            appointmentRepository.save(appointment);
        }
        return "redirect:/bac-si-phong-kham/quan-ly-lich-hen";
    }
	
	@PostMapping("/huy-xac-nhan-kham")
    public String cancelConfirmation(@RequestParam("appointmentId") Integer appointmentId, HttpSession session) {
        
        AppointmentEntity appointment = appointmentRepository.findById(appointmentId).orElse(null);
        if (appointment != null && appointment.getStatus() == AppointmentEntity.Status.Đã_xác_nhận) {
            appointment.setStatus(AppointmentEntity.Status.Chưa_xác_nhận);
            appointmentRepository.save(appointment);
        }
        return "redirect:/bac-si-phong-kham/quan-ly-lich-hen";
    }
	
    @GetMapping("/dang-xuat")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/bac-si-phong-kham/dang-nhap";
    }
    
    @GetMapping("/sua-lich-hen")
    public String editPatient(@RequestParam("patientId") Integer patientId, @RequestParam("appointmentId") Integer appointmentId, Model model, HttpSession session) {
        
        PatientsEntity patient = patientsService.findPatientByEmail(appointmentRepository.findById(appointmentId).get().getPatient().getEmail());
        AppointmentEntity appointment = appointmentRepository.findById(appointmentId)
            .orElseThrow(() -> new RuntimeException("Lịch hẹn không tồn tại"));
        model.addAttribute("patient", patient);
        model.addAttribute("appointment", appointment);
        return "suaBN";
    }
	
	@PostMapping("/sua-lich-hen")
    public String updatePatient(
            @RequestParam("patientId") Integer patientId,
            @RequestParam("appointmentId") Integer appointmentId,
            @RequestParam("fullname") String fullname,
            @RequestParam("gender") String gender,
            @RequestParam("dateofbirth") String dateofbirth,
            @RequestParam("phone") String phone,
            @RequestParam("email") String email,
            @RequestParam("address") String address,
            @RequestParam("reason") String reason,
            @RequestParam("appointmentdate") String appointmentdate,
            @RequestParam("appointmenttime") String appointmenttime,
            @RequestParam("status") String status,
            Model model,
            HttpSession session) {
        UsersEntity loggedInUser = (UsersEntity) session.getAttribute("loggedInUser");
        if (loggedInUser == null || loggedInUser.getRole() != UsersEntity.Role.doctor) {
            return "redirect:/bac-si-phong-kham/dang-nhap";
        }

        try {
            // Update Patient
            PatientsEntity patient = patientsService.findPatientByEmail(email);
            if (patient == null) {
                patient = new PatientsEntity();
                patient.setPatientid(patientId);
            }
            patient.setFullname(fullname);
            patient.setGender(PatientsEntity.Gender.valueOf(gender.toLowerCase()));
            patient.setDateofbirth(LocalDate.parse(dateofbirth));
            patient.setPhone(phone);
            patient.setEmail(email);
            patient.setAddress(address);
            patientsService.savePatient(patient);

            // Update Appointment
            AppointmentEntity appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Lịch hẹn không tồn tại"));
            appointment.setPatient(patient);
            appointment.setReason(reason);
            appointment.setAppointmentdate(LocalDate.parse(appointmentdate));
            appointment.setAppointmenttime(LocalTime.parse(appointmenttime));
            appointment.setStatus(AppointmentEntity.Status.valueOf(status));
            appointmentRepository.save(appointment);

            return "redirect:/bac-si-phong-kham/quan-ly-lich-hen";
        } catch (Exception e) {
            model.addAttribute("error", "Lỗi khi cập nhật thông tin: " + e.getMessage());
            AppointmentEntity appointment = appointmentRepository.findById(appointmentId).get();
            PatientsEntity patient = patientsService.findPatientByEmail(appointment.getPatient().getEmail());
            model.addAttribute("patient", patient);
            model.addAttribute("appointment", appointment);
            return "edit-patient";
        }
    }
	
	@PostMapping("/xoa-lich-hen")
	public String deleteAppointment(@RequestParam("id") Integer appointmentId, Model model, HttpSession session) {
	    
	    try {
	        appointmentService.deleteAppointment(appointmentId);
	        return "redirect:/bac-si-phong-kham/quan-ly-lich-hen";
	    } catch (Exception e) {
	        model.addAttribute("error", "Không thể xóa lịch hẹn: " + e.getMessage());
	        List<AppointmentEntity> appointments = appointmentRepository.findAll();
	        model.addAttribute("appointments", appointments);
	        return "quanlylichhen";
	    }
	}
	
	
	@GetMapping("/them-bac-si")
    public String showAddDoctorForm(Model model) {
        model.addAttribute("doctor", new DoctorsEntity());
        return "themBS";
    }

	@PostMapping("/them-bac-si")
    public String addDoctor(
            @RequestParam("fullname") String fullname,
            @RequestParam("department") String department,
            @RequestParam("phone") String phone,
            @RequestParam("email") String email,
            @RequestParam("workinghours") String workinghours,
            @RequestParam(value = "qualifications", required = false) String qualifications,
            @RequestParam(value = "address", required = false) String address,
            @RequestParam("gender") String gender,
            @RequestParam(value = "profileImage", required = false) MultipartFile profileImage,
            @RequestParam("status") String status,
            Model model) {
        try {
            DoctorsEntity doctor = new DoctorsEntity();
            doctor.setFullname(fullname);
            doctor.setDepartment(department);
            doctor.setPhone(phone);
            doctor.setEmail(email);
            doctor.setWorkinghours(workinghours);
            doctor.setQualifications(qualifications);
            doctor.setAddress(address);
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
                        return "themBS";
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

                    // Set the file path in the entity
                    doctor.setProfileImage("/images/" + uniqueFilename);
                } catch (IOException e) {
                    model.addAttribute("error", "Lỗi khi tải lên ảnh: " + e.getMessage());
                    model.addAttribute("doctor", doctor);
                    return "themBS";
                }
            }

            doctorService.saveDoctor(doctor);
            return "redirect:/bac-si-phong-kham/quan-ly-bac-si?success=true";
        } catch (Exception e) {
            model.addAttribute("error", "Lỗi khi thêm bác sĩ: " + e.getMessage());
            model.addAttribute("doctor", new DoctorsEntity());
            return "themBS";
        }
    }
}
