package Nhom2.DoAnWeb.NTUScientistInfor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import Nhom2.DoAnWeb.NTUScientistInfor.model.GiaithuongEntity;
@Repository
public interface GiaiThuongRepository extends JpaRepository<GiaithuongEntity, String>{
	@Query("SELECT g FROM GiaithuongEntity g WHERE g.tenGiaiThuong LIKE %:keyword%")
    List<GiaithuongEntity> findByTenGiaiThuongContaining(@Param("keyword") String keyword);

    @Query("SELECT g FROM GiaithuongEntity g JOIN g.nhanGiaiThuong ngt JOIN NhaKhoaHoc nkh ON ngt.nkhId = nkh.id WHERE nkh.hoTen LIKE %:keyword%")
    List<GiaithuongEntity> findByNhaKhoaHocHoTenContaining(@Param("keyword") String keyword);
    
    @Query("SELECT g.noiDung, COUNT(g) FROM GiaithuongEntity g GROUP BY g.noiDung")
    List<Object[]> thongKeTheoNoiDung();
}
