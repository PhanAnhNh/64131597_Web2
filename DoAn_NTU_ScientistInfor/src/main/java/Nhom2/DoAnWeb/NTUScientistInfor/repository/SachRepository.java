package Nhom2.DoAnWeb.NTUScientistInfor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Nhom2.DoAnWeb.NTUScientistInfor.model.SachEntity;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
@Repository
public interface SachRepository extends JpaRepository<SachEntity, String>{
	SachEntity findTopByOrderByIdDesc();
	
	@Query("SELECT s.loaiSach, COUNT(s) FROM SachEntity s GROUP BY s.loaiSach")
    List<Object[]> thongKeTheoLoaiSach();
	
}
