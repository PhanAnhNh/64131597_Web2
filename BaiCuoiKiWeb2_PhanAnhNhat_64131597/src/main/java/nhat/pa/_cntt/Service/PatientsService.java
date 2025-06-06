package nhat.pa._cntt.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nhat.pa._cntt.Entity.PatientsEntity;
import nhat.pa._cntt.Repository.PatientReponsitory;

@Service
public class PatientsService {
	@Autowired
    private PatientReponsitory patientRepository;

    public PatientsEntity savePatient(PatientsEntity patient) {
        return patientRepository.save(patient);
    }

    public PatientsEntity findPatientByEmail(String email) {
        return patientRepository.findByEmail(email);
    }
    
    public PatientsEntity findById(Integer patientId) {
        return patientRepository.findById(patientId).orElse(null);
    }


    public long countPatients() {
        return patientRepository.count();
    }
}
