package Nhom2.DoAnWeb.NTUScientistInfor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import Nhom2.DoAnWeb.NTUScientistInfor.model.BaiBaoKHEntity;

public interface BaiBaoKHRepository extends JpaRepository<BaiBaoKHEntity, String>{
	BaiBaoKHEntity findTopByOrderByBaiBaoIdDesc();
    long countByTapChiId(String tapChiId);

    // Search by title (case-insensitive)
    List<BaiBaoKHEntity> findByTenBaiBaoContainingIgnoreCase(String keyword);

    // Search by author ID through SangTac relationship
    @Query("SELECT DISTINCT bb FROM BaiBaoKHEntity bb JOIN bb.sangTacs st WHERE st.nkhId = :nkhId")
    List<BaiBaoKHEntity> findBySangTacNkhId(@Param("nkhId") Integer nkhId);

    // Search by both title (case-insensitive) and author ID
    @Query("SELECT DISTINCT bb FROM BaiBaoKHEntity bb JOIN bb.sangTacs st WHERE LOWER(bb.tenBaiBao) LIKE LOWER(CONCAT('%', :keyword, '%')) AND st.nkhId = :nkhId")
    List<BaiBaoKHEntity> findByTenBaiBaoContainingIgnoreCaseAndSangTacNkhId(@Param("keyword") String keyword, @Param("nkhId") Integer nkhId);
    
    //-------------------------------------------------------
    @Query("SELECT t.phamVi, t.mucChatLuong, COUNT(b) " +
            "FROM BaiBaoKHEntity b JOIN tapchiKHHTKHEntity t ON b.tapChiId = t.tapChiId " +
            "GROUP BY t.phamVi, t.mucChatLuong")
     List<Object[]> countBaiBaoByPhamViAndMucChatLuong();
     
     @Query("SELECT t.mucChatLuong, COUNT(b) " +
            "FROM BaiBaoKHEntity b JOIN tapchiKHHTKHEntity t ON b.tapChiId = t.tapChiId " +
            "WHERE t.phamVi = 'Trong_nước' " +
            "GROUP BY t.mucChatLuong")
     List<Object[]> countBaiBaoTrongNuocByMucChatLuong();
     
     @Query("SELECT t.mucChatLuong, COUNT(b) " +
            "FROM BaiBaoKHEntity b JOIN tapchiKHHTKHEntity t ON b.tapChiId = t.tapChiId " +
            "WHERE t.phamVi = 'Nước_ngoài' " +
            "GROUP BY t.mucChatLuong")
     List<Object[]> countBaiBaoQuocTeByMucChatLuong();
}
