package Nhom2.DoAnWeb.NTUScientistInfor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Nhom2.DoAnWeb.NTUScientistInfor.model.TaiKhoanEntity;
@Repository
public interface TaikhoanReponsitory extends JpaRepository<TaiKhoanEntity, String>{
	
}
