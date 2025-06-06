package Nhom2.DoAnWeb.NTUScientistInfor.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import Nhom2.DoAnWeb.NTUScientistInfor.model.SachEntity;
import Nhom2.DoAnWeb.NTUScientistInfor.model.SachEntity.LoaiSach;
import Nhom2.DoAnWeb.NTUScientistInfor.model.TacGiaSachEntity;
import Nhom2.DoAnWeb.NTUScientistInfor.repository.NhaKhoaHocReponsitory;
import Nhom2.DoAnWeb.NTUScientistInfor.repository.SachRepository;
import Nhom2.DoAnWeb.NTUScientistInfor.repository.TacGiaSachReponsitory;
import jakarta.transaction.Transactional;

@Service
public class SachService {
	 @Autowired
	    private SachRepository sachRepository;

	    @Autowired
	    private TacGiaSachReponsitory tacGiaSachRepository;

	    @Autowired
	    private NhaKhoaHocReponsitory nhaKhoaHocReponsitory;

	    public List<SachEntity> getAllSach() {
	        return sachRepository.findAll();
	    }

	    // Tìm kiếm sách theo tên, tác giả, và loại sách
	    public List<SachEntity> searchSach(String keyword, Integer nkhId, SachEntity.LoaiSach loaiSach) {
	        List<SachEntity> allSach = sachRepository.findAll();

	        // Nếu không có tiêu chí tìm kiếm, trả về tất cả
	        if ((keyword == null || keyword.trim().isEmpty()) && nkhId == null && loaiSach == null) {
	            return allSach;
	        }

	        // Lọc theo tiêu chí
	        return allSach.stream()
	                .filter(sach -> {
	                    boolean matchesKeyword = keyword == null || keyword.trim().isEmpty() ||
	                            sach.getTenSach().toLowerCase().contains(keyword.toLowerCase());

	                    boolean matchesAuthor = nkhId == null;
	                    if (nkhId != null) {
	                        List<TacGiaSachEntity> tacGiaList = tacGiaSachRepository.findBySachId(sach.getId());
	                        matchesAuthor = tacGiaList.stream()
	                                .anyMatch(tg -> tg.getNkhId() == nkhId);
	                    }

	                    boolean matchesType = loaiSach == null || sach.getLoaiSach() == loaiSach;

	                    return matchesKeyword && matchesAuthor && matchesType;
	                })
	                .collect(Collectors.toList());
	    }

	    // Lấy danh sách tác giả theo ID sách
	    public List<TacGiaSachEntity> findTacGiaBySachId(String sachId) {
	        return tacGiaSachRepository.findBySachId(sachId);
	    }
	    
	    public String generateSachId(SachEntity.LoaiSach loaiSach) {
	        // Map LoaiSach to prefix
	        String prefix;
	        switch (loaiSach) {
	            case Giáo_trình:
	                prefix = "GT";
	                break;
	            case Sách_chuyên_khảo:
	                prefix = "SCK";
	                break;
	            case Chương_sách:
	                prefix = "CS";
	                break;
	            case Sách_tham_khảo:
	                prefix = "STK";
	                break;
	            default:
	                throw new IllegalArgumentException("Loại sách không hợp lệ: " + loaiSach);
	        }
	        
	        String finalPrefix = prefix;
	        List<SachEntity> matchingBooks = sachRepository.findAll().stream()
	                .filter(sach -> sach.getId().startsWith(finalPrefix))
	                .sorted((a, b) -> b.getId().compareTo(a.getId())) // Sort descending
	                .collect(Collectors.toList());

	        if (matchingBooks.isEmpty()) {
	            return prefix + "001";
	        }

	        String lastId = matchingBooks.get(0).getId();
	        try {
	            // Extract numeric part (e.g., "007" from "STK007")
	            String numericPart = lastId.replaceAll("[^0-9]", "");
	            if (numericPart.isEmpty()) {
	                return prefix + "001"; // Fallback if no numeric part
	            }
	            int number = Integer.parseInt(numericPart) + 1;
	            return prefix + String.format("%03d", number);
	        } catch (NumberFormatException e) {
	            // Log error and fallback
	            System.err.println("Error parsing ID: " + lastId + ". Falling back to default ID.");
	            return prefix + "001";
	        }
	    }

	    @Transactional
	    public SachEntity addSach(SachEntity sach, List<Integer> tacGiaIds) {
	        // Generate ID based on LoaiSach
	        if (sach.getId() == null || sach.getId().isEmpty()) {
	            sach.setId(generateSachId(sach.getLoaiSach()));
	        }

	        // Save book
	        SachEntity savedSach = sachRepository.save(sach);

	        // Save authors
	        if (tacGiaIds != null && !tacGiaIds.isEmpty()) {
	            for (Integer nkhId : tacGiaIds) {
	                if (nhaKhoaHocReponsitory.existsById(nkhId)) {
	                    TacGiaSachEntity tacGiaSach = new TacGiaSachEntity(nkhId, savedSach.getId());
	                    tacGiaSachRepository.save(tacGiaSach);
	                }
	            }
	        }

	        return savedSach;
	    }
	    
	    @Transactional
	    public SachEntity updateSach(String oldId, SachEntity sach, List<Integer> tacGiaIds) {
	        if (!sachRepository.existsById(oldId)) {
	            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sách không tồn tại với ID: " + oldId);
	        }

	        // Check if loaiSach has changed and update ID if necessary
	        SachEntity existingSach = sachRepository.findById(oldId).get();
	        if (sach.getLoaiSach() != existingSach.getLoaiSach()) {
	            String newId = generateSachId(sach.getLoaiSach());
	            sach.setId(newId);
	            // Delete old book and its authors to avoid duplicate IDs
	            tacGiaSachRepository.deleteBySachId(oldId);
	            sachRepository.deleteById(oldId);
	        } else {
	            sach.setId(oldId);
	        }

	        // Save updated book
	        SachEntity savedSach = sachRepository.save(sach);

	        // Update authors
	        tacGiaSachRepository.deleteBySachId(savedSach.getId());
	        if (tacGiaIds != null && !tacGiaIds.isEmpty()) {
	            for (Integer nkhId : tacGiaIds) {
	                if (nhaKhoaHocReponsitory.existsById(nkhId)) {
	                    TacGiaSachEntity tacGiaSach = new TacGiaSachEntity(nkhId, savedSach.getId());
	                    tacGiaSachRepository.save(tacGiaSach);
	                }
	            }
	        }

	        return savedSach;
	    }
	    
	    @Transactional
	    public boolean deleteSach(String sachId) {
	        try {
	            // Xóa các bản ghi tác giả trước
	            tacGiaSachRepository.deleteBySachId(sachId);
	            
	            // Sau đó xóa sách
	            sachRepository.deleteById(sachId);
	            return true;
	        } catch (Exception e) {
	            System.err.println("Lỗi khi xóa sách: " + e.getMessage());
	            return false;
	        }
	    }
	    
	    public Map<String, Object> getThongKeSach() {
	        List<Object[]> results = sachRepository.thongKeTheoLoaiSach();
	        Map<String, Long> thongKeMap = new HashMap<>();

	        // Initialize counts for all LoaiSach
	        for (LoaiSach loaiSach : LoaiSach.values()) {
	            thongKeMap.put(loaiSach.toString(), 0L);
	        }

	        // Populate counts from query results
	        for (Object[] result : results) {
	            String loaiSach = ((LoaiSach) result[0]).toString();
	            Long count = (Long) result[1];
	            thongKeMap.put(loaiSach, count);
	        }

	        // Calculate total
	        long total = thongKeMap.values().stream().mapToLong(Long::longValue).sum();

	        // Return map with counts and total
	        Map<String, Object> resultMap = new HashMap<>();
	        resultMap.put("counts", thongKeMap);
	        resultMap.put("total", total);

	        return resultMap;
	    }
}
