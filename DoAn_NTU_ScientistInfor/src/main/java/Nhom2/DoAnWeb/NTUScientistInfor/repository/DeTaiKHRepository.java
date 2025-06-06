package Nhom2.DoAnWeb.NTUScientistInfor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import Nhom2.DoAnWeb.NTUScientistInfor.model.DetaiKHEntitty;
import Nhom2.DoAnWeb.NTUScientistInfor.model.DetaiKHEntitty.CapDeTai;
@Repository
public interface DeTaiKHRepository extends JpaRepository<DetaiKHEntitty, String>{
	@Query("SELECT dt FROM DetaiKHEntitty dt WHERE LOWER(dt.tenDeTai) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(dt.mucTieu) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<DetaiKHEntitty> searchByKeyword(String keyword);

	List<DetaiKHEntitty> findByCapDeTai(CapDeTai capDeTai);

    List<DetaiKHEntitty> findByTenDeTaiContainingIgnoreCaseOrMucTieuContainingIgnoreCase(String tenDeTai, String mucTieu);

    List<DetaiKHEntitty> findByTenDeTaiContainingIgnoreCaseOrMucTieuContainingIgnoreCaseAndCapDeTai(String tenDeTai, String mucTieu, CapDeTai capDeTai);

    DetaiKHEntitty findTopByOrderByDeTaiIdDesc();
    
    @Query("SELECT d.capDeTai, d.donViChuTri, COUNT(d) FROM DetaiKHEntitty d GROUP BY d.capDeTai, d.donViChuTri")
    List<Object[]> thongKeTheoCapDeTaiVaDonVi();
    
}
