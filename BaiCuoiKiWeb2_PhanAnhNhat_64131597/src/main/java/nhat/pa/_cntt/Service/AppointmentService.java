package nhat.pa._cntt.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nhat.pa._cntt.Entity.AppointmentEntity;
import nhat.pa._cntt.Entity.DoctorsEntity;
import nhat.pa._cntt.Entity.PatientsEntity;
import nhat.pa._cntt.Repository.AppointmentReponsitory;
import nhat.pa._cntt.Repository.DoctorRepository;
import nhat.pa._cntt.Repository.PatientReponsitory;


@Service
public class AppointmentService {
	@Autowired
    private AppointmentReponsitory appointmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientReponsitory patientRepository;

    public AppointmentEntity bookAppointment(String fullName, String gender, String phone, String email, String address,
                                      Integer doctorId, LocalDateTime appointmentDateTime) {
        // Validate doctor
        DoctorsEntity doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Bác sĩ không tồn tại"));

        // Validate working hours
        if (!isDoctorAvailable(doctor, appointmentDateTime)) {
            throw new RuntimeException("Bác sĩ không làm việc vào thời điểm này");
        }

        // Check for conflicting appointments
        LocalDate appointmentDate = appointmentDateTime.toLocalDate();
        List<AppointmentEntity> conflictingAppointments = appointmentRepository
                .findByDoctorDoctoridAndAppointmentdate(doctorId, appointmentDate);
        for (AppointmentEntity existing : conflictingAppointments) {
            if (existing.getAppointmenttime().equals(appointmentDateTime.toLocalTime())) {
                throw new RuntimeException("Lịch hẹn đã được đặt vào thời điểm này");
            }
        }

        // Create or find patient
        PatientsEntity patient = patientRepository.findByEmail(email);
        if (patient == null) {
            patient = new PatientsEntity();
            patient.setFullname(fullName);
            patient.setGender(PatientsEntity.Gender.valueOf(gender.toLowerCase()));
            patient.setPhone(phone);
            patient.setEmail(email);
            patient.setAddress(address);
            patient.setCreatedAt(LocalDateTime.now());
            patient = patientRepository.save(patient);
        }

        // Create appointment
        AppointmentEntity appointment = new AppointmentEntity();
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setAppointmentdate(appointmentDate);
        appointment.setAppointmenttime(appointmentDateTime.toLocalTime());
        appointment.setStatus(AppointmentEntity.Status.Chưa_xác_nhận);
        appointment.setCreatedAt(LocalDateTime.now());

        return appointmentRepository.save(appointment);
    }

    private boolean isDoctorAvailable(DoctorsEntity doctor, LocalDateTime appointmentDateTime) {
        String workingHours = doctor.getWorkinghours(); // e.g., "mon,wed,fri: 08:00-12:00"
        if (workingHours == null) {
            return false;
        }

        String[] parts = workingHours.split(": ");
        String[] days = parts[0].split(",");
        String[] timeRange = parts[1].split("-");
        LocalTime startTime = LocalTime.parse(timeRange[0], DateTimeFormatter.ofPattern("HH:mm"));
        LocalTime endTime = LocalTime.parse(timeRange[1], DateTimeFormatter.ofPattern("HH:mm"));

        // Check if the appointment day matches doctor's working days
        String appointmentDay = appointmentDateTime.getDayOfWeek().toString().toLowerCase().substring(0, 3);
        boolean isWorkingDay = false;
        for (String day : days) {
            if (day.toLowerCase().startsWith(appointmentDay)) {
                isWorkingDay = true;
                break;
            }
        }
        if (!isWorkingDay) {
            return false;
        }

        // Check if the appointment time is within working hours
        LocalTime appointmentTime = appointmentDateTime.toLocalTime();
        return !appointmentTime.isBefore(startTime) && !appointmentTime.isAfter(endTime);
    }

    public List<AppointmentEntity> findAppointmentsByDoctorAndDate(Integer doctorId, LocalDate date) {
        return appointmentRepository.findByDoctorDoctoridAndAppointmentdate(doctorId, date);
    }

    public List<AppointmentEntity> findAppointmentsByPatient(Integer patientId) {
        return appointmentRepository.findByPatientPatientid(patientId);
    }

    public AppointmentEntity confirmAppointment(Integer appointmentId) {
        AppointmentEntity appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Lịch hẹn không tồn tại"));
        appointment.setStatus(AppointmentEntity.Status.Đã_xác_nhận);
        return appointmentRepository.save(appointment);
    }
    
    public void deleteAppointment(Integer appointmentId) {
        AppointmentEntity appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Lịch hẹn không tồn tại"));
        appointmentRepository.delete(appointment);
    }
    
    //Thống kê
    public long countAppointmentsToday(LocalDate today) {
        return appointmentRepository.findByAppointmentdate(today).size();
    }

    public Map<String, Long> getWeeklyAppointments(LocalDate today) {
        LocalDate startOfWeek = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate endOfWeek = startOfWeek.plusDays(6);

        List<AppointmentEntity> appointments = appointmentRepository.findByAppointmentdateBetween(startOfWeek, endOfWeek);
        Map<LocalDate, Long> dailyCounts = appointments.stream()
                .collect(Collectors.groupingBy(AppointmentEntity::getAppointmentdate, Collectors.counting()));

        Map<String, Long> weeklyCounts = new HashMap<>();
        for (int i = 0; i < 7; i++) {
            LocalDate date = startOfWeek.plusDays(i);
            String dayLabel = switch (i) {
                case 0 -> "Thứ 2";
                case 1 -> "Thứ 3";
                case 2 -> "Thứ 4";
                case 3 -> "Thứ 5";
                case 4 -> "Thứ 6";
                case 5 -> "Thứ 7";
                case 6 -> "CN";
                default -> "";
            };
            weeklyCounts.put(dayLabel, dailyCounts.getOrDefault(date, 0L));
        }
        return weeklyCounts;
    }

    public List<Map<String, Object>> getTopDoctorsByAppointments() {
        List<AppointmentEntity> appointments = appointmentRepository.findAll();
        Map<DoctorsEntity, Long> doctorCounts = appointments.stream()
                .collect(Collectors.groupingBy(AppointmentEntity::getDoctor, Collectors.counting()));

        return doctorCounts.entrySet().stream()
                .sorted(Map.Entry.<DoctorsEntity, Long>comparingByValue().reversed())
                .limit(3)
                .map(entry -> {
                    Map<String, Object> doctorInfo = new HashMap<>();
                    DoctorsEntity doctor = entry.getKey();
                    doctorInfo.put("fullname", doctor.getFullname());
                    doctorInfo.put("department", doctor.getDepartment());
                    doctorInfo.put("appointmentCount", entry.getValue());
                    return doctorInfo;
                })
                .collect(Collectors.toList());
    }
}
