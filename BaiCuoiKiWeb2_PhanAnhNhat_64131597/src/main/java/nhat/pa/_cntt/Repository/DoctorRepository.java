package nhat.pa._cntt.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import nhat.pa._cntt.Entity.DoctorsEntity;

@Repository
public interface DoctorRepository extends JpaRepository<DoctorsEntity, Integer>{
	List<DoctorsEntity> findByDepartment(String department);
    List<DoctorsEntity> findByStatus(DoctorsEntity.Status status);
    Optional<DoctorsEntity> findByEmail(String email);
    
    @Query("SELECT d FROM DoctorsEntity d LEFT JOIN d.user u WHERE u.role != 'admin' OR u.role IS NULL")
    List<DoctorsEntity> findDoctorsWithDoctorRole();
    
    @Query("SELECT d FROM DoctorsEntity d LEFT JOIN d.user u WHERE (u.role = 'doctor' OR u IS NULL) AND d.status = 'hoạt_động'")
    List<DoctorsEntity> findDoctorsWithDoctorRoleState();
}
