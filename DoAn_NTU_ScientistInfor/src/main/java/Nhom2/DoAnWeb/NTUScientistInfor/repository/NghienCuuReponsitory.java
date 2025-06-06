package Nhom2.DoAnWeb.NTUScientistInfor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import Nhom2.DoAnWeb.NTUScientistInfor.model.NghienCuuEntity;

@Repository
public interface NghienCuuReponsitory extends JpaRepository<NghienCuuEntity, NghienCuuEntity.NghienCuuId>{
	@Modifying
    @Query("DELETE FROM NghienCuuEntity n WHERE n.detaiId = :detaiId")
    void deleteByDetaiId(@Param("detaiId") String detaiId);

    void deleteByNkhId(Integer nkhId);

    long countByNkhId(Integer nkhId);
    
    long countByDetaiId(String detaiId);
}
