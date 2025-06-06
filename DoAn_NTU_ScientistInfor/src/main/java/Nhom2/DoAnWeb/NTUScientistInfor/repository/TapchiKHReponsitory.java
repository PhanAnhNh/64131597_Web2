package Nhom2.DoAnWeb.NTUScientistInfor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Nhom2.DoAnWeb.NTUScientistInfor.model.tapchiKHHTKHEntity;
@Repository
public interface TapchiKHReponsitory extends JpaRepository<tapchiKHHTKHEntity, String>{
	
}
