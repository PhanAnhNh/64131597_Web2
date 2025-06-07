package Nhom2.DoAnWeb.NTUScientistInfor.service;

import Nhom2.DoAnWeb.NTUScientistInfor.model.NhaKhoaHoc;
import Nhom2.DoAnWeb.NTUScientistInfor.model.NhanSHTTEntity;
import Nhom2.DoAnWeb.NTUScientistInfor.model.SoHuuTriTueEntity;
import Nhom2.DoAnWeb.NTUScientistInfor.repository.NhaKhoaHocReponsitory;
import Nhom2.DoAnWeb.NTUScientistInfor.repository.NhanSHTTRepository;
import Nhom2.DoAnWeb.NTUScientistInfor.repository.SoHuuTriTueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SHTTService {

    @Autowired
    private SoHuuTriTueRepository soHuuTriTueRepository;

    @Autowired
    private NhanSHTTRepository nhanSHTTRepository;

    @Autowired
    private NhaKhoaHocReponsitory nhaKhoaHocRepository;

    public List<SoHuuTriTueEntity> getAllSHTT() {
        return soHuuTriTueRepository.findAll();
    }

    public List<SoHuuTriTueEntity> searchSHTT(String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            return getAllSHTT();
        }
        return soHuuTriTueRepository.findAll().stream()
                .filter(shtt -> shtt.getTenBang().toLowerCase().contains(keyword.toLowerCase()) ||
                        shtt.getNhanSHTT().stream()
                                .anyMatch(nshtt -> nshtt.getNhaKhoaHoc().getHoTen().toLowerCase().contains(keyword.toLowerCase())))
                .collect(Collectors.toList());
    }

    public Optional<SoHuuTriTueEntity> getSHTTById(String id) {
        return soHuuTriTueRepository.findById(id)
            .map(shtt -> {
                // Eager load nhanSHTT relationships
                shtt.setNhanSHTT(nhanSHTTRepository.findByShttId(id));
                return shtt;
            });
    }

    public void saveSHTT(SoHuuTriTueEntity shtt, List<Integer> nkhIds) {
        soHuuTriTueRepository.save(shtt);
        if (nkhIds != null && !nkhIds.isEmpty()) {
            for (Integer nkhId : nkhIds) {
                NhanSHTTEntity nhanSHTT = new NhanSHTTEntity(nkhId, shtt.getId());
                nhanSHTTRepository.save(nhanSHTT);
            }
        }
    }

    public void updateSHTT(SoHuuTriTueEntity shtt, List<Integer> nkhIds) {
        // Xóa tất cả các mối quan hệ cũ
        nhanSHTTRepository.deleteAll(nhanSHTTRepository.findAll().stream()
                .filter(nshtt -> nshtt.getShttId().equals(shtt.getId()))
                .collect(Collectors.toList()));
        
        // Cập nhật thông tin SHTT
        soHuuTriTueRepository.save(shtt);
        
        // Thêm lại các mối quan hệ mới
        if (nkhIds != null && !nkhIds.isEmpty()) {
            for (Integer nkhId : nkhIds) {
                NhanSHTTEntity nhanSHTT = new NhanSHTTEntity(nkhId, shtt.getId());
                nhanSHTTRepository.save(nhanSHTT);
            }
        }
    }

    public void deleteSHTT(String id) {
        nhanSHTTRepository.deleteAll(nhanSHTTRepository.findAll().stream()
                .filter(nshtt -> nshtt.getShttId().equals(id))
                .collect(Collectors.toList()));
        soHuuTriTueRepository.deleteById(id);
    }

    public List<NhaKhoaHoc> getAllNhaKhoaHoc() {
        return nhaKhoaHocRepository.findAll();
    }
    
    public Map<String, Object> getThongKeSHTT() {
        List<Object[]> results = soHuuTriTueRepository.thongKeTheoToChucCap();
        List<Map<String, Object>> thongKeList = new ArrayList<>();
        long total = 0;

        for (Object[] result : results) {
            SoHuuTriTueEntity.ToChucCap toChucCap = (SoHuuTriTueEntity.ToChucCap) result[0];
            Long count = (Long) result[1];
            Map<String, Object> stat = new HashMap<>();
            String displayName = toChucCap != null ? toChucCap.toString().replace("_", " ") : "Không xác định";
            stat.put("toChucCap", displayName);
            stat.put("soLuong", count);
            thongKeList.add(stat);
            total += count;
        }

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("thongKe", thongKeList);
        resultMap.put("total", total);

        return resultMap;
    }
}