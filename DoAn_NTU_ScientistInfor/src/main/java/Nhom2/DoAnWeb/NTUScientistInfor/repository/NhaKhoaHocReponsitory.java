package Nhom2.DoAnWeb.NTUScientistInfor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import Nhom2.DoAnWeb.NTUScientistInfor.model.NhaKhoaHoc;

@Repository
public interface NhaKhoaHocReponsitory extends JpaRepository<NhaKhoaHoc, Integer>{
	@Query("SELECT n FROM NhaKhoaHoc n WHERE " +
	           "(LOWER(n.hoTen) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
	           "CAST(n.id AS string) LIKE CONCAT('%', :keyword, '%') OR " +
	           "LOWER(n.email) LIKE LOWER(CONCAT('%', :keyword, '%'))) AND " +
	           "(n.hocVi = :hocVi OR :hocVi IS NULL OR :hocVi = '')")
	    List<NhaKhoaHoc> searchByKeywordAndHocVi(String keyword, String hocVi);

	    @Query("SELECT COUNT(dt) FROM NhaKhoaHoc nkh JOIN nkh.nkh_nghiencuu dt WHERE nkh.id = :nkhId")
	    int countDeTaiByNkhId(@Param("nkhId") int nkhId);
	    NhaKhoaHoc findByTenTaiKhoan(String tenTaiKhoan);
	    
	    boolean existsById(Integer id);
	    
	    @Query("SELECT n.nganhDaoTao, n.hocVi, COUNT(n) FROM NhaKhoaHoc n GROUP BY n.nganhDaoTao, n.hocVi")
	    List<Object[]> thongKeTheoHocVi();
}

