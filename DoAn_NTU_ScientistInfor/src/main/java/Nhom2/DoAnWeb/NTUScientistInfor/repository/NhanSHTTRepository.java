package Nhom2.DoAnWeb.NTUScientistInfor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import Nhom2.DoAnWeb.NTUScientistInfor.model.NhanSHTTEntity;
@Repository
public interface NhanSHTTRepository extends JpaRepository<NhanSHTTEntity, NhanSHTTEntity.NhanshttId>{
	void deleteByNkhId(Integer nkhId);
	List<NhanSHTTEntity> findByShttId(String shttId);
}
