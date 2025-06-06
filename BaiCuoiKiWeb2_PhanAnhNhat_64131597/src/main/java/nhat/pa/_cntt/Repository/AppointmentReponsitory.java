package nhat.pa._cntt.Repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import nhat.pa._cntt.Entity.AppointmentEntity;

@Repository
public interface AppointmentReponsitory extends JpaRepository<AppointmentEntity, Integer> {
	List<AppointmentEntity> findByDoctorDoctoridAndAppointmentdate(Integer doctorId, LocalDate date);
    List<AppointmentEntity> findByPatientPatientid(Integer patientId);
    
    List<AppointmentEntity> findByAppointmentdate(LocalDate date);
    List<AppointmentEntity> findByAppointmentdateBetween(LocalDate startDate, LocalDate endDate);
}
