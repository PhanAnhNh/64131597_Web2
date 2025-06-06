package Nhom2.DoAnWeb.NTUScientistInfor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import Nhom2.DoAnWeb.NTUScientistInfor.model.SangTacEntity;
import Nhom2.DoAnWeb.NTUScientistInfor.model.SangTacId;
import jakarta.transaction.Transactional;

public interface SangTacRepository extends JpaRepository<SangTacEntity, SangTacId>{
	List<SangTacEntity> findByBaiBaoId(String baiBaoId);
	
	@Modifying
    @Query("DELETE FROM SangTacEntity st WHERE st.baiBaoId = :baiBaoId")
    void deleteByBaiBaoId(String baiBaoId);

	long countByNkhId(Integer nkhId);

	void deleteByNkhId(Integer nkhId);

}
