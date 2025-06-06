package nhat.pa._cntt.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import nhat.pa._cntt.Entity.PatientsEntity;

@Repository
public interface PatientReponsitory extends JpaRepository<PatientsEntity, Integer>{
	PatientsEntity findByEmail(String email);
}
