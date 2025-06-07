package Nhom2.DoAnWeb.NTUScientistInfor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import Nhom2.DoAnWeb.NTUScientistInfor.model.TacGiaSachEntity;
@Repository
public interface TacGiaSachReponsitory extends JpaRepository<TacGiaSachEntity, TacGiaSachEntity.TacGiaSachId>{
	List<TacGiaSachEntity> findBySachId(String sachId);
	void deleteBySachId(String sachId);
	
	void deleteByNkhId(Integer nkhId);
    long countByNkhId(Integer nkhId);
}
