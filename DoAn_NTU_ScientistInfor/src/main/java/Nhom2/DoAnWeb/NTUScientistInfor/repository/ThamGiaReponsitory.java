package Nhom2.DoAnWeb.NTUScientistInfor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Nhom2.DoAnWeb.NTUScientistInfor.model.ThamGiaCTEntity;
@Repository
public interface ThamGiaReponsitory extends JpaRepository<ThamGiaCTEntity, ThamGiaCTEntity.ThamgiacongtrinhId>{
	void deleteByNkhId(Integer nkhId);
}
