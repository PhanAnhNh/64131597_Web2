package Nhom2.DoAnWeb.NTUScientistInfor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import Nhom2.DoAnWeb.NTUScientistInfor.model.NhanGTEntity;

@Repository
public interface NhanGTRepository extends JpaRepository<NhanGTEntity, NhanGTEntity.NhangiaithuongId>{
	void deleteByNkhId(Integer nkhId);
	
	@Modifying
    @Query("DELETE FROM NhanGTEntity ngt WHERE ngt.gtId = :gtId")
    void deleteByGtId(@Param("gtId") String gtId);
}
