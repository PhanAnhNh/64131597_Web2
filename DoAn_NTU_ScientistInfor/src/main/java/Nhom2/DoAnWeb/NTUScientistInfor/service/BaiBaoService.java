package Nhom2.DoAnWeb.NTUScientistInfor.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import Nhom2.DoAnWeb.NTUScientistInfor.model.BaiBaoKHEntity;
import Nhom2.DoAnWeb.NTUScientistInfor.model.SangTacEntity;
import Nhom2.DoAnWeb.NTUScientistInfor.model.tapchiKHHTKHEntity;
import Nhom2.DoAnWeb.NTUScientistInfor.model.tapchiKHHTKHEntity.PhamViEnum;
import Nhom2.DoAnWeb.NTUScientistInfor.repository.BaiBaoKHRepository;
import Nhom2.DoAnWeb.NTUScientistInfor.repository.NhaKhoaHocReponsitory;
import Nhom2.DoAnWeb.NTUScientistInfor.repository.SangTacRepository;
import Nhom2.DoAnWeb.NTUScientistInfor.repository.TapchiKHReponsitory;
import jakarta.transaction.Transactional;


@Service
public class BaiBaoService{
	
	 @Autowired
	    private BaiBaoKHRepository baiBaoKHRepository;
	 
	 @Autowired
	    private TapchiKHReponsitory tapchiKHReponsitory;
	    
	    @Autowired
	    private SangTacRepository sangTacRepository;
	    
	    @Autowired
	    private NhaKhoaHocReponsitory nhaKhoaHocReponsitory;

	    // Lấy tất cả bài báo khoa học
	    public List<BaiBaoKHEntity> getAllBaiBaoKH() {
	        return baiBaoKHRepository.findAll();
	    }
	    
	    
	 // Tìm kiếm bài báo theo tên hoặc tác giả
	    public List<BaiBaoKHEntity> searchBaiBao(String keyword, Integer nkhId) {
	        List<BaiBaoKHEntity> allBaiBao = baiBaoKHRepository.findAll();
	        
	        // Nếu không có từ khóa và không có tác giả, trả về tất cả
	        if ((keyword == null || keyword.trim().isEmpty()) && nkhId == null) {
	            return allBaiBao;
	        }

	        // Lọc theo từ khóa (tên bài báo) và/hoặc tác giả
	        return allBaiBao.stream()
	            .filter(baiBao -> {
	                boolean matchesKeyword = keyword == null || keyword.trim().isEmpty() || 
	                    baiBao.getTenBaiBao().toLowerCase().contains(keyword.toLowerCase());
	                
	                boolean matchesAuthor = nkhId == null;
	                if (nkhId != null) {
	                    List<SangTacEntity> sangTacList = sangTacRepository.findByBaiBaoId(baiBao.getBaiBaoId());
	                    matchesAuthor = sangTacList.stream()
	                        .anyMatch(st -> st.getNkhId().equals(nkhId));
	                }
	                
	                return matchesKeyword && matchesAuthor;
	            })
	            .collect(Collectors.toList());
	    }

	    // Lấy bài báo khoa học theo ID
	    public BaiBaoKHEntity getBaiBaoKHById(String baiBaoId) {
	        Optional<BaiBaoKHEntity> result = baiBaoKHRepository.findById(baiBaoId);
	        return result.orElse(null);
	    }
	    
	    
	 // Phương thức sinh mã bài báo tự động
	    public String generateBaiBaoId() {
	        String prefix = "B";
	        BaiBaoKHEntity lastBaiBao = baiBaoKHRepository.findTopByOrderByBaiBaoIdDesc();
	        
	        if (lastBaiBao == null) {
	            return prefix + "001";
	        }
	        
	        String lastId = lastBaiBao.getBaiBaoId();
	        int number = Integer.parseInt(lastId.replace(prefix, "")) + 1;
	        return prefix + String.format("%03d", number);
	    }
	   
	    @Transactional
	    public BaiBaoKHEntity addBaiBao(BaiBaoKHEntity baiBao, List<Integer> tacGiaIds) {
	        // Kiểm tra xem tạp chí đã tồn tại chưa
	        Optional<tapchiKHHTKHEntity> tapChiExist = tapchiKHReponsitory.findById(baiBao.getTapChiId());
	        
	        // Nếu tạp chí chưa tồn tại, tạo mới
	        if (!tapChiExist.isPresent()) {
	            tapchiKHHTKHEntity newTapChi = new tapchiKHHTKHEntity();
	            newTapChi.setTapChiId(baiBao.getTapChiId());
	            baiBao.setTapChiId(newTapChi.getTapChiId());
	            // Các thông tin khác có thể để null hoặc set giá trị mặc định
	            tapchiKHReponsitory.save(newTapChi);
	        }
	        
	        // Lưu bài báo
	        BaiBaoKHEntity savedBaiBao = baiBaoKHRepository.save(baiBao);
	        
	        // Lưu thông tin tác giả (sáng tác)
	        if (tacGiaIds != null && !tacGiaIds.isEmpty()) {
	            for (Integer nkhId : tacGiaIds) {
	                SangTacEntity sangTac = new SangTacEntity();
	                sangTac.setNkhId(nkhId);
	                sangTac.setBaiBaoId(savedBaiBao.getBaiBaoId());
	                
	                // Kiểm tra xem nhà khoa học có tồn tại không
	                if (nhaKhoaHocReponsitory.existsById(nkhId)) {
	                    sangTacRepository.save(sangTac);
	                }
	            }
	        }
	        
	        return savedBaiBao;
	    }
	    
	    
	 // Add these methods to your BaiBaoService class

	    @Transactional
	    public void deleteBaiBao(String baiBaoId) {
	        
	        BaiBaoKHEntity baiBao = baiBaoKHRepository.findById(baiBaoId)
	            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Bài báo không tồn tại"));
	        
	        String tapChiId = baiBao.getTapChiId();
	        
	        
	        sangTacRepository.deleteByBaiBaoId(baiBaoId);
	        
	       
	        baiBaoKHRepository.deleteById(baiBaoId);
	        
	        Optional<tapchiKHHTKHEntity> tapChiOpt = tapchiKHReponsitory.findById(tapChiId);
	        if (tapChiOpt.isPresent()) {
	            tapchiKHHTKHEntity tapChi = tapChiOpt.get();
	            
	            if (isAutoCreatedJournal(tapChi)) {
	               
	                tapchiKHReponsitory.deleteById(tapChiId);
	            }
	        }
	    }

	    private boolean isAutoCreatedJournal(tapchiKHHTKHEntity tapChi) {
	        return tapChi.getTenTapChi() == null && 
	               tapChi.getToChuc() == null && 
	               tapChi.getPhamVi() == null && 
	               tapChi.getMucChatLuong() == null && 
	               tapChi.getNamDang() == null;
	    }
	    
	    //Sửa bài báo
	    @Transactional
	    public BaiBaoKHEntity updateBaiBao(BaiBaoKHEntity baiBao, List<Integer> tacGiaIds) {
	        // Kiểm tra bài báo tồn tại
	        BaiBaoKHEntity existingBaiBao = baiBaoKHRepository.findById(baiBao.getBaiBaoId())
	            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Bài báo không tồn tại"));
	        
	        // Cập nhật thông tin bài báo
	        existingBaiBao.setTenBaiBao(baiBao.getTenBaiBao());
	        existingBaiBao.setLinhVuc(baiBao.getLinhVuc());
	        existingBaiBao.setNoiXuatBan(baiBao.getNoiXuatBan());
	        existingBaiBao.setNamDang(baiBao.getNamDang());
	        existingBaiBao.setLienKet(baiBao.getLienKet());
	        
	        // Xóa các tác giả cũ
	        sangTacRepository.deleteByBaiBaoId(baiBao.getBaiBaoId());
	        
	        // Thêm các tác giả mới
	        if (tacGiaIds != null && !tacGiaIds.isEmpty()) {
	            for (Integer nkhId : tacGiaIds) {
	                if (nhaKhoaHocReponsitory.existsById(nkhId)) {
	                    SangTacEntity sangTac = new SangTacEntity();
	                    sangTac.setNkhId(nkhId);
	                    sangTac.setBaiBaoId(baiBao.getBaiBaoId());
	                    sangTacRepository.save(sangTac);
	                }
	            }
	        }
	        
	        return baiBaoKHRepository.save(existingBaiBao);
	    }
	    
	    public Optional<tapchiKHHTKHEntity> getTapChiById(String tapChiId) {
	        return tapchiKHReponsitory.findById(tapChiId);
	    }
	    
	    @Transactional
	    public void updateTapChi(tapchiKHHTKHEntity tapChi) {
	        // Check if the journal exists
	        if (tapChi.getTapChiId() == null) {
	            throw new IllegalArgumentException("TapChiId cannot be null");
	        }
	        tapchiKHReponsitory.findById(tapChi.getTapChiId())
	            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tạp chí không tồn tại: " + tapChi.getTapChiId()));

	        // Save the updated journal
	        tapchiKHReponsitory.save(tapChi);
	    }
	    
	    public Map<String, Object> getThongKeBaiBao() {
	        Map<String, Object> result = new HashMap<>();
	        
	        // Thống kê tổng hợp
	        List<Object[]> tongHop = baiBaoKHRepository.countBaiBaoByPhamViAndMucChatLuong();
	        
	        // Thống kê bài báo trong nước
	        List<Object[]> trongNuoc = baiBaoKHRepository.countBaiBaoTrongNuocByMucChatLuong();
	        long tongTrongNuoc = trongNuoc.stream().mapToLong(arr -> (Long) arr[1]).sum();
	        
	        // Thống kê bài báo quốc tế
	        List<Object[]> quocTe = baiBaoKHRepository.countBaiBaoQuocTeByMucChatLuong();
	        long tongQuocTe = quocTe.stream().mapToLong(arr -> (Long) arr[1]).sum();
	        
	        // Tổng số bài báo
	        long tongBaiBao = tongTrongNuoc + tongQuocTe;
	        
	        // Phân loại theo mức chất lượng
	        Map<String, Long> trongNuocByChatLuong = new HashMap<>();
	        for (Object[] obj : trongNuoc) {
	            String mucChatLuong = (String) obj[0];
	            Long count = (Long) obj[1];
	            trongNuocByChatLuong.put(mucChatLuong, count);
	        }
	        
	        Map<String, Long> quocTeByChatLuong = new HashMap<>();
	        for (Object[] obj : quocTe) {
	            String mucChatLuong = (String) obj[0];
	            Long count = (Long) obj[1];
	            quocTeByChatLuong.put(mucChatLuong, count);
	        }
	        
	        // Đếm số bài báo theo từng loại tạp chí quốc tế
	        long scopusQ1 = quocTeByChatLuong.getOrDefault("Scopus Q1", 0L);
	        long scopusQ2 = quocTeByChatLuong.getOrDefault("Scopus Q2", 0L);
	        long esci = quocTeByChatLuong.getOrDefault("ESCI", 0L);
	        
	        // Đếm số bài báo theo từng loại tạp chí trong nước
	        long hdcdgs = trongNuocByChatLuong.getOrDefault("HĐCDGS", 0L);
	        long khac = tongTrongNuoc - hdcdgs;
	        
	        result.put("tongBaiBao", tongBaiBao);
	        result.put("tongTrongNuoc", tongTrongNuoc);
	        result.put("tongQuocTe", tongQuocTe);
	        result.put("trongNuocHDCDGS", hdcdgs);
	        result.put("trongNuocKhac", khac);
	        result.put("quocTeScopusQ1", scopusQ1);
	        result.put("quocTeScopusQ2", scopusQ2);
	        result.put("quocTeESCI", esci);
	        
	        return result;
	    }
}
