package Nhom2.DoAnWeb.NTUScientistInfor.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Nhom2.DoAnWeb.NTUScientistInfor.model.DetaiKHEntitty;
import Nhom2.DoAnWeb.NTUScientistInfor.model.DetaiKHEntitty.CapDeTai;
import Nhom2.DoAnWeb.NTUScientistInfor.model.DetaiKHEntitty.DonViChuTri;
import Nhom2.DoAnWeb.NTUScientistInfor.model.NhaKhoaHoc;
import Nhom2.DoAnWeb.NTUScientistInfor.model.NghienCuuEntity;
import Nhom2.DoAnWeb.NTUScientistInfor.repository.DeTaiKHRepository;
import Nhom2.DoAnWeb.NTUScientistInfor.repository.NhaKhoaHocReponsitory;
import Nhom2.DoAnWeb.NTUScientistInfor.repository.NghienCuuReponsitory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class DeTaiKHService {

    private static final Logger logger = LoggerFactory.getLogger(DeTaiKHService.class);

    @Autowired
    private DeTaiKHRepository deTaiKHRepository;

    @Autowired
    private NhaKhoaHocReponsitory nhaKhoaHocRepository;

    @Autowired
    private NghienCuuReponsitory nghienCuuRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public List<DetaiKHEntitty> getAllDeTai() {
        return deTaiKHRepository.findAll();
    }

    public Optional<DetaiKHEntitty> getDeTaiById(String id) {
        return deTaiKHRepository.findById(id);
    }

    @Transactional
    public void saveDeTaiKH(DetaiKHEntitty deTaiKH, List<Integer> nkhIds) {
        logger.info("Saving DeTaiKH with ID: {}, nkhIds: {}", deTaiKH.getDeTaiId(), nkhIds);

        // Initialize nhaKhoaHocs if null
        if (deTaiKH.getNhaKhoaHocs() == null) {
            deTaiKH.setNhaKhoaHocs(new ArrayList<>());
        }

        // Save the project first to ensure DETAI_ID exists
        DetaiKHEntitty savedDeTai = deTaiKHRepository.save(deTaiKH);
        logger.info("Saved DeTaiKH with ID: {}", savedDeTai.getDeTaiId());

        // Handle scientists if nkhIds is provided
        if (nkhIds != null && !nkhIds.isEmpty()) {
            List<NhaKhoaHoc> nhaKhoaHocs = nhaKhoaHocRepository.findAllById(nkhIds);
            if (nhaKhoaHocs.size() != nkhIds.size()) {
                logger.warn("Some NKH IDs not found: Requested {}, Found {}", nkhIds, nhaKhoaHocs.size());
                throw new IllegalArgumentException("Một hoặc nhiều nhà khoa học không tồn tại.");
            }

            // Synchronize @ManyToMany relationship
            savedDeTai.setNhaKhoaHocs(nhaKhoaHocs);
            for (NhaKhoaHoc nkh : nhaKhoaHocs) {
                if (nkh.getNkh_nghiencuu() == null) {
                    nkh.setNkh_nghiencuu(new ArrayList<>());
                }
                if (!nkh.getNkh_nghiencuu().contains(savedDeTai)) {
                    nkh.getNkh_nghiencuu().add(savedDeTai);
                }

                // Create NghienCuuEntity for the relationship
                NghienCuuEntity nghienCuu = new NghienCuuEntity(nkh.getId(), savedDeTai.getDeTaiId());
                if (!nghienCuuRepository.existsById(new NghienCuuEntity.NghienCuuId(nkh.getId(), savedDeTai.getDeTaiId()))) {
                    nghienCuuRepository.save(nghienCuu);
                    logger.info("Saved NghienCuuEntity for NKH_ID: {}, DETAI_ID: {}", nkh.getId(), savedDeTai.getDeTaiId());
                }
                // Save NhaKhoaHoc to persist nkh_nghiencuu changes
                nhaKhoaHocRepository.save(nkh);
            }
        }

        // Save the project again to persist @ManyToMany changes
        deTaiKHRepository.save(savedDeTai);
        logger.info("Completed saving DeTaiKH with ID: {}", savedDeTai.getDeTaiId());
    }

    @Transactional
    public void updateDeTaiKH(DetaiKHEntitty deTaiKH, List<Integer> nkhIds) {
        logger.info("Updating DeTaiKH with ID: {}, nkhIds: {}", deTaiKH.getDeTaiId(), nkhIds);

        Optional<DetaiKHEntitty> existingDeTai = deTaiKHRepository.findById(deTaiKH.getDeTaiId());
        if (existingDeTai.isEmpty()) {
            logger.error("DeTaiKH with ID {} not found", deTaiKH.getDeTaiId());
            throw new IllegalArgumentException("Đề tài với ID " + deTaiKH.getDeTaiId() + " không tồn tại.");
        }

        DetaiKHEntitty updatedDeTai = existingDeTai.get();
        // Update project fields
        updatedDeTai.setTenDeTai(deTaiKH.getTenDeTai());
        updatedDeTai.setCapDeTai(deTaiKH.getCapDeTai());
        updatedDeTai.setDonViChuTri(deTaiKH.getDonViChuTri());
        updatedDeTai.setMucTieu(deTaiKH.getMucTieu());
        updatedDeTai.setNoiDung(deTaiKH.getNoiDung());
        updatedDeTai.setKetQua(deTaiKH.getKetQua());
        updatedDeTai.setNamBatDau(deTaiKH.getNamBatDau());
        updatedDeTai.setNamKetThuc(deTaiKH.getNamKetThuc());
        updatedDeTai.setTinhTrang(deTaiKH.getTinhTrang());
        updatedDeTai.setXepLoai(deTaiKH.getXepLoai());

        // Remove all existing NghienCuuEntity entries for this project
     // Trong DeTaiKHService.java, phương thức updateDeTaiKH
        nghienCuuRepository.deleteByDetaiId(updatedDeTai.getDeTaiId());
        logger.info("Deleted all NghienCuuEntity entries for DETAI_ID: {}", updatedDeTai.getDeTaiId());

        // Kiểm tra xem tất cả bản ghi đã được xóa
        long count = nghienCuuRepository.countByDetaiId(updatedDeTai.getDeTaiId());
        if (count > 0) {
            logger.error("Failed to delete all NghienCuuEntity entries for DETAI_ID: {}", updatedDeTai.getDeTaiId());
            throw new IllegalStateException("Không thể xóa hết các bản ghi NghienCuuEntity cho DETAI_ID: " + updatedDeTai.getDeTaiId());
        }

        entityManager.flush();
        // Flush changes to ensure NghienCuuEntity deletions are committed
        entityManager.flush();

        // Add new associations
        if (nkhIds != null && !nkhIds.isEmpty()) {
        	// Loại bỏ trùng lặp
            List<Integer> uniqueNkhIds = new ArrayList<>(new LinkedHashSet<>(nkhIds));
            List<NhaKhoaHoc> nhaKhoaHocs = nhaKhoaHocRepository.findAllById(uniqueNkhIds);
            if (nhaKhoaHocs.size() != uniqueNkhIds.size()) {
                logger.warn("Some NKH IDs not found: Requested {}, Found {}", uniqueNkhIds, nhaKhoaHocs.size());
                throw new IllegalArgumentException("Một hoặc nhiều nhà khoa học không tồn tại.");
            }
            // Tiếp tục xử lý với nhaKhoaHocs
            updatedDeTai.setNhaKhoaHocs(nhaKhoaHocs);
            updatedDeTai.setNhaKhoaHocs(nhaKhoaHocs);
            for (NhaKhoaHoc nkh : nhaKhoaHocs) {
                if (nkh.getNkh_nghiencuu() == null) {
                    nkh.setNkh_nghiencuu(new ArrayList<>());
                }
                if (!nkh.getNkh_nghiencuu().contains(updatedDeTai)) {
                    nkh.getNkh_nghiencuu().add(updatedDeTai);
                }

                // Kiểm tra xem bản ghi đã tồn tại chưa
                NghienCuuEntity nghienCuu = new NghienCuuEntity(nkh.getId(), updatedDeTai.getDeTaiId());
                if (!nghienCuuRepository.existsById(new NghienCuuEntity.NghienCuuId(nkh.getId(), updatedDeTai.getDeTaiId()))) {
                    nghienCuuRepository.save(nghienCuu);
                    logger.info("Saved NghienCuuEntity for NKH_ID: {}, DETAI_ID: {}", nkh.getId(), updatedDeTai.getDeTaiId());
                } else {
                    logger.info("NghienCuuEntity for NKH_ID: {}, DETAI_ID: {} already exists, skipping save", nkh.getId(), updatedDeTai.getDeTaiId());
                }

                nhaKhoaHocRepository.save(nkh);
            }
        }

        // Save the project to persist all changes
        deTaiKHRepository.save(updatedDeTai);
        logger.info("Completed updating DeTaiKH with ID: {}", updatedDeTai.getDeTaiId());
    }

    @Transactional
    public void deleteDeTaiKH(String id) {
        logger.info("Deleting DeTaiKH with ID: {}", id);

        Optional<DetaiKHEntitty> deTaiKH = deTaiKHRepository.findById(id);
        if (deTaiKH.isEmpty()) {
            logger.error("DeTaiKH with ID {} not found", id);
            throw new IllegalArgumentException("Đề tài với ID " + id + " không tồn tại.");
        }

        DetaiKHEntitty deTai = deTaiKH.get();

        // Delete all NghienCuuEntity entries for this project
        nghienCuuRepository.deleteByDetaiId(deTai.getDeTaiId());
        logger.info("Deleted all NghienCuuEntity entries for DETAI_ID: {}", deTai.getDeTaiId());

        // Clear associations
        for (NhaKhoaHoc nkh : deTai.getNhaKhoaHocs()) {
            nkh.getNkh_nghiencuu().remove(deTai);
            nhaKhoaHocRepository.save(nkh);
            logger.info("Removed DeTaiKH {} from NhaKhoaHoc {} and saved", deTai.getDeTaiId(), nkh.getId());
        }
        deTai.getNhaKhoaHocs().clear();

        // Flush changes to ensure NghienCuuEntity deletions are committed
        entityManager.flush();

        // Delete the project
        deTaiKHRepository.deleteById(id);
        logger.info("Completed deleting DeTaiKH with ID: {}", id);
    }

    public List<DetaiKHEntitty> searchProjects(String keyword, CapDeTai capDeTai) {
        if (keyword == null || keyword.trim().isEmpty()) {
            if (capDeTai == null) {
                return getAllDeTai();
            }
            return deTaiKHRepository.findByCapDeTai(capDeTai);
        }
        if (capDeTai == null) {
            return deTaiKHRepository.findByTenDeTaiContainingIgnoreCaseOrMucTieuContainingIgnoreCase(keyword, keyword);
        }
        return deTaiKHRepository.findByTenDeTaiContainingIgnoreCaseOrMucTieuContainingIgnoreCaseAndCapDeTai(keyword, keyword, capDeTai);
    }

    public List<NhaKhoaHoc> getAllNKH() {
        return nhaKhoaHocRepository.findAll();
    }
    
    public List<Map<String, Object>> getThongKeDeTai() {
        List<Object[]> results = deTaiKHRepository.thongKeTheoCapDeTaiVaDonVi();
        List<Map<String, Object>> thongKeList = new ArrayList<>();
        
        CapDeTai[] capDeTais = CapDeTai.values();
        Map<String, Map<String, Long>> thongKeMap = new HashMap<>();
        
        for (Object[] result : results) {
            String capDeTai = ((CapDeTai) result[0]).toString();
            String donViChuTri = ((DonViChuTri) result[1]).toString();
            Long count = (Long) result[2];
            
            thongKeMap.computeIfAbsent(donViChuTri, k -> new HashMap<>()).put(capDeTai, count);
        }
        
        int stt = 1;
        long total = 0;
        for (String donViChuTri : thongKeMap.keySet()) {
            Map<String, Object> row = new HashMap<>();
            row.put("stt", stt++);
            row.put("donViChuTri", donViChuTri);
            
            long rowTotal = 0;
            for (CapDeTai cap : capDeTais) {
                String capDeTaiStr = cap.toString();
                long count = thongKeMap.get(donViChuTri).getOrDefault(capDeTaiStr, 0L);
                row.put(capDeTaiStr, count);
                rowTotal += count;
            }
            row.put("tongCong", rowTotal);
            total += rowTotal;
            row.put("ghiChu", "");
            
            thongKeList.add(row);
        }
        
        if (!thongKeList.isEmpty()) {
            Map<String, Object> totalRow = new HashMap<>();
            totalRow.put("stt", "");
            totalRow.put("donViChuTri", "Tổng cộng");
            for (CapDeTai cap : capDeTais) {
                String capDeTaiStr = cap.toString();
                long sum = thongKeList.stream()
                    .mapToLong(row -> (Long) row.getOrDefault(capDeTaiStr, 0L))
                    .sum();
                totalRow.put(capDeTaiStr, sum);
            }
            totalRow.put("tongCong", total);
            totalRow.put("ghiChu", "");
            thongKeList.add(totalRow);
        }
        
        return thongKeList;
    }
}