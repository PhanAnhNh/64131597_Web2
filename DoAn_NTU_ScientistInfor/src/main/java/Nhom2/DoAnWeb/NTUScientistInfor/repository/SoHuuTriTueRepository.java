package Nhom2.DoAnWeb.NTUScientistInfor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import Nhom2.DoAnWeb.NTUScientistInfor.model.SoHuuTriTueEntity;
@Repository
public interface SoHuuTriTueRepository extends JpaRepository<SoHuuTriTueEntity, String>{
	@Query("SELECT s.toChucCap, COUNT(s) FROM SoHuuTriTueEntity s GROUP BY s.toChucCap")
    List<Object[]> thongKeTheoToChucCap();
}
