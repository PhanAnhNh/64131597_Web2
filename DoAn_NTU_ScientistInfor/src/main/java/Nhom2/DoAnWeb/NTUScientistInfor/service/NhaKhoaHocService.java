package Nhom2.DoAnWeb.NTUScientistInfor.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import Nhom2.DoAnWeb.NTUScientistInfor.model.NhaKhoaHoc;
import Nhom2.DoAnWeb.NTUScientistInfor.model.TaiKhoanEntity;
import Nhom2.DoAnWeb.NTUScientistInfor.repository.GiaiThuongRepository;
import Nhom2.DoAnWeb.NTUScientistInfor.repository.NghienCuuReponsitory;
import Nhom2.DoAnWeb.NTUScientistInfor.repository.NhaKhoaHocReponsitory;
import Nhom2.DoAnWeb.NTUScientistInfor.repository.NhanGTRepository;
import Nhom2.DoAnWeb.NTUScientistInfor.repository.NhanSHTTRepository;
import Nhom2.DoAnWeb.NTUScientistInfor.repository.SangTacRepository;
import Nhom2.DoAnWeb.NTUScientistInfor.repository.TacGiaSachReponsitory;
import Nhom2.DoAnWeb.NTUScientistInfor.repository.ThamGiaReponsitory;
import jakarta.transaction.Transactional;

@Service
public class NhaKhoaHocService {

	@Autowired
    private NhaKhoaHocReponsitory nhaKhoaHocRepository;

    @Autowired
    private TaiKhoanService taiKhoanService;

    @Autowired
    private SangTacRepository sangTacRepository;
    
    @Autowired
    private NghienCuuReponsitory nghienCuuRepository;

    @Autowired
    private NhanGTRepository nhanGTRepository;

    @Autowired
    private NhanSHTTRepository nhanSHTTRepository;

    @Autowired
    private TacGiaSachReponsitory tacGiaSachRepository;

    @Autowired
    private ThamGiaReponsitory thamGiaCongTrinhRepository;
    

    public List<NhaKhoaHoc> getAllNhaKhoaHoc() {
        return nhaKhoaHocRepository.findAll();
    }

    public NhaKhoaHoc getNhaKhoaHocById(Integer id) {
        Optional<NhaKhoaHoc> result = nhaKhoaHocRepository.findById(id);
        return result.orElse(null);
    }

    public void addNKH(NhaKhoaHoc nkh, MultipartFile hinhAnhFile) throws IOException {
        // Kiểm tra xem NKH_ID đã tồn tại chưa
        if (nhaKhoaHocRepository.existsById(nkh.getId())) {
            throw new IllegalArgumentException("Mã nhà khoa học đã tồn tại.");
        }

        // Kiểm tra tài khoản
        TaiKhoanEntity taiKhoan = taiKhoanService.findByTenTaiKhoan(nkh.getTenTaiKhoan());
        if (taiKhoan == null) {
            throw new IllegalArgumentException("Tài khoản không tồn tại.");
        }
        nkh.setTaiKhoan(taiKhoan);

        // Xử lý hình ảnh
        if (!hinhAnhFile.isEmpty()) {
            String fileName = System.currentTimeMillis() + "_" + hinhAnhFile.getOriginalFilename();
            Path filePath = Paths.get("src/main/resources/static/images/" + fileName);
            Files.createDirectories(filePath.getParent());
            Files.write(filePath, hinhAnhFile.getBytes());
            nkh.setHinhAnh("/images/" + fileName);
        }

        nhaKhoaHocRepository.save(nkh);
    }

    public NhaKhoaHoc findById(Integer id) {
        Optional<NhaKhoaHoc> result = nhaKhoaHocRepository.findById(id);
        return result.orElse(null);
    }

    public void updateNKH(Integer id, NhaKhoaHoc updatedNkh, MultipartFile hinhAnhFile) throws IOException {
        NhaKhoaHoc existingNkh = getNhaKhoaHocById(id);
        if (existingNkh != null) {
            existingNkh.setHoTen(updatedNkh.getHoTen());
            existingNkh.setGioiTinh(updatedNkh.getGioiTinh());
            existingNkh.setNamSinh(updatedNkh.getNamSinh());
            existingNkh.setEmail(updatedNkh.getEmail());
            existingNkh.setHocVi(updatedNkh.getHocVi());
            existingNkh.setNganhDaoTao(updatedNkh.getNganhDaoTao());
            existingNkh.setLinhVucNghienCuu(updatedNkh.getLinhVucNghienCuu());
            existingNkh.setChuyenMon(updatedNkh.getChuyenMon());

            if (!hinhAnhFile.isEmpty()) {
                if (hinhAnhFile.getSize() > 5 * 1024 * 1024) {
                    throw new IllegalArgumentException("Kích thước file không được vượt quá 5MB.");
                }
                String originalFileName = hinhAnhFile.getOriginalFilename();
                if (originalFileName != null && (originalFileName.endsWith(".jpg") || originalFileName.endsWith(".png") || originalFileName.endsWith(".jpeg"))) {
                    if (existingNkh.getHinhAnh() != null && !existingNkh.getHinhAnh().isEmpty() && !existingNkh.getHinhAnh().equals("NULL")) {
                        Path oldFilePath = Paths.get("src/main/resources/static" + existingNkh.getHinhAnh());
                        Files.deleteIfExists(oldFilePath);
                    }
                    String fileName = System.currentTimeMillis() + "_" + originalFileName;
                    Path filePath = Paths.get("src/main/resources/static/images/" + fileName);
                    Files.createDirectories(filePath.getParent());
                    Files.write(filePath, hinhAnhFile.getBytes());
                    existingNkh.setHinhAnh("/images/" + fileName);
                } else {
                    throw new IllegalArgumentException("Chỉ chấp nhận file hình ảnh định dạng .jpg, .jpeg hoặc .png");
                }
            }

            TaiKhoanEntity taiKhoan = taiKhoanService.findByTenTaiKhoan(updatedNkh.getTenTaiKhoan());
            if (taiKhoan != null) {
                existingNkh.setTaiKhoan(taiKhoan);
            }
            nhaKhoaHocRepository.save(existingNkh);
        }
    }

    public List<NhaKhoaHoc> searchNKH(String keyword, String hocVi) {
        if (keyword == null && hocVi == null) {
            return getAllNhaKhoaHoc();
        }
        return nhaKhoaHocRepository.searchByKeywordAndHocVi(keyword, hocVi);
    }

    public long getTongBaiBaoByNkhId(Integer nkhId) {
        return sangTacRepository.countByNkhId(nkhId);
    }

    public long getTongSoSachByNkhId(Integer nkhId) {
        // Placeholder: No Sach entity defined in the model
        return 0;
    }

    public long getTongSoDeTaiByNkhId(Integer nkhId) {
        return nhaKhoaHocRepository.countDeTaiByNkhId(nkhId);
    }
    
    @Transactional
    public void deleteNKH(Integer nkhId) {
        NhaKhoaHoc nkh = getNhaKhoaHocById(nkhId);
        if (nkh == null) {
            throw new IllegalArgumentException("Nhà khoa học không tồn tại.");
        }

        // Xóa các bản ghi liên quan trong các bảng quan hệ
        nghienCuuRepository.deleteByNkhId(nkhId);
        nhanGTRepository.deleteByNkhId(nkhId);
        nhanSHTTRepository.deleteByNkhId(nkhId);
        sangTacRepository.deleteByNkhId(nkhId);
        tacGiaSachRepository.deleteByNkhId(nkhId);
        thamGiaCongTrinhRepository.deleteByNkhId(nkhId);

        // Xóa hình ảnh nếu có
        if (nkh.getHinhAnh() != null && !nkh.getHinhAnh().isEmpty() && !nkh.getHinhAnh().equals("NULL")) {
            try {
                Path filePath = Paths.get("src/main/resources/static" + nkh.getHinhAnh());
                Files.deleteIfExists(filePath);
            } catch (IOException e) {
                // Log lỗi nhưng không làm gián đoạn quá trình xóa
                System.err.println("Lỗi khi xóa hình ảnh: " + e.getMessage());
            }
        }

        // Xóa nhà khoa học
        nhaKhoaHocRepository.deleteById(nkhId);

        // Xóa tài khoản liên quan (nếu cần)
        TaiKhoanEntity taiKhoan = taiKhoanService.findByTenTaiKhoan(nkh.getTenTaiKhoan());
        if (taiKhoan != null) {
            taiKhoanService.deleteTaiKhoan(taiKhoan.getTenTaiKhoan());
        }
    }
    //---------------------------------------------------
    public List<Map<String, Object>> getThongKeTheoHocVi() {
        List<Object[]> results = nhaKhoaHocRepository.thongKeTheoHocVi();
        List<Map<String, Object>> thongKeList = new ArrayList<>();
        
        // Các học vị cố định
        String[] hocVis = {"Tiến sĩ khoa học", "Tiến sĩ", "Thạc sĩ", "Đại học", "Khác"};
        
        // Tạo map để lưu trữ thống kê theo đơn vị
        Map<String, Map<String, Long>> thongKeMap = new HashMap<>();
        
        // Xử lý kết quả từ query
        for (Object[] result : results) {
            String donVi = (String) result[0];
            String hocVi = (String) result[1];
            Long count = (Long) result[2];
            
            thongKeMap.computeIfAbsent(donVi, k -> new HashMap<>()).put(hocVi, count);
        }
        
        // Chuyển đổi thành danh sách để hiển thị
        int stt = 1;
        for (String donVi : thongKeMap.keySet()) {
            Map<String, Object> row = new HashMap<>();
            row.put("stt", stt++);
            row.put("donVi", donVi);
            
            // Đảm bảo tất cả học vị đều có giá trị, nếu không có thì đặt là 0
            for (String hocVi : hocVis) {
                row.put(hocVi, thongKeMap.get(donVi).getOrDefault(hocVi, 0L));
            }
            
            thongKeList.add(row);
        }
        
        return thongKeList;
    }
}
