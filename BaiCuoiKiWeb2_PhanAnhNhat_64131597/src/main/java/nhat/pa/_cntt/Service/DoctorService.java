package nhat.pa._cntt.Service;

import java.io.ObjectInputFilter.Status;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nhat.pa._cntt.Entity.DoctorsEntity;
import nhat.pa._cntt.Entity.UsersEntity;
import nhat.pa._cntt.Repository.DoctorRepository;

@Service
public class DoctorService {
	@Autowired
    private DoctorRepository doctorRepository;
	
	public List<DoctorsEntity> findDoctorsWithDoctorRole() {
        return doctorRepository.findDoctorsWithDoctorRole();
    }
	
	public List<DoctorsEntity> findDoctorsWithDoctorRolestate() {
        return doctorRepository.findDoctorsWithDoctorRoleState();
    }
	
	public DoctorsEntity registerDoctor(UsersEntity user, String fullname, String phone, String email) {
        DoctorsEntity doctor = new DoctorsEntity();
        doctor.setUser(user);
        doctor.setFullname(fullname);
        doctor.setPhone(phone);
        doctor.setEmail(email);
        doctor.setStatus(DoctorsEntity.Status.hoạt_động);
        return doctorRepository.save(doctor);
    }

    public List<DoctorsEntity> findDoctorsByDepartment(String department) {
        return doctorRepository.findByDepartment(department);
    }

    public List<DoctorsEntity> findActiveDoctors() {
        return doctorRepository.findByStatus(DoctorsEntity.Status.hoạt_động);
    }

    public DoctorsEntity saveDoctor(DoctorsEntity doctor) {
        return doctorRepository.save(doctor);
    }

    // Thêm phương thức findByEmail vào DoctorService
    public Optional<DoctorsEntity> findByEmail(String email) {
        return doctorRepository.findByEmail(email);
    }
    
    public DoctorsEntity findById(Integer doctorId) {
        return doctorRepository.findById(doctorId)
            .orElseThrow(() -> new RuntimeException("Bác sĩ không tồn tại"));
    }
    
    public void deleteDoctor(Integer doctorId) {
        DoctorsEntity doctor = findById(doctorId);
        doctorRepository.delete(doctor);
    }
    
    public long countDoctors() {
        return doctorRepository.count();
    }

    public long countDepartments() {
        return doctorRepository.findAll().stream()
                .map(DoctorsEntity::getDepartment)
                .distinct()
                .count();
    }
}
