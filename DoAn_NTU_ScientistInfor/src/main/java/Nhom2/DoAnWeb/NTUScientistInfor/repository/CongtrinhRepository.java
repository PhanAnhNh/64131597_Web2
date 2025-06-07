package Nhom2.DoAnWeb.NTUScientistInfor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import Nhom2.DoAnWeb.NTUScientistInfor.model.CongtrinhEntity;

@Repository
public interface CongtrinhRepository extends JpaRepository<CongtrinhEntity, String>{
	@Query("SELECT d FROM CongtrinhEntity d LEFT JOIN FETCH d.thamGia")
    List<CongtrinhEntity> getAllWithThamGia();
	@Query("SELECT d FROM CongtrinhEntity d WHERE LOWER(d.tenCongTrinh) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<CongtrinhEntity> searchByKeyword(String keyword);
	@Query("SELECT c.quyMo, COUNT(c) FROM CongtrinhEntity c GROUP BY c.quyMo")
    List<Object[]> thongKeTheoQuyMo();
}
