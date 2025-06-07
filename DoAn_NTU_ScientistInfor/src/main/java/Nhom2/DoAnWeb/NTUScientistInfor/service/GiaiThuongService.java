package Nhom2.DoAnWeb.NTUScientistInfor.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Nhom2.DoAnWeb.NTUScientistInfor.model.GiaithuongEntity;
import Nhom2.DoAnWeb.NTUScientistInfor.model.NhanGTEntity;
import Nhom2.DoAnWeb.NTUScientistInfor.repository.GiaiThuongRepository;
import Nhom2.DoAnWeb.NTUScientistInfor.repository.NhanGTRepository;

@Service
public class GiaiThuongService {

    @Autowired
    private GiaiThuongRepository giaiThuongRepository;

    @Autowired
    private NhanGTRepository nhanGTRepository;

    public List<GiaithuongEntity> getAllAwards() {
        return giaiThuongRepository.findAll();
    }

    public GiaithuongEntity getAwardById(String id) {
        return giaiThuongRepository.findById(id).orElse(null);
    }

    public GiaithuongEntity saveAward(GiaithuongEntity award) {
        return giaiThuongRepository.save(award);
    }

    @Transactional
    public void deleteAward(String id) {
        nhanGTRepository.deleteByGtId(id); // Delete associated records first
        giaiThuongRepository.deleteById(id);
    }

    public List<GiaithuongEntity> searchAwardsByName(String keyword) {
        return giaiThuongRepository.findByTenGiaiThuongContaining(keyword);
    }

    public List<GiaithuongEntity> searchAwardsByScientist(String keyword) {
        return giaiThuongRepository.findByNhaKhoaHocHoTenContaining(keyword);
    }

    public void saveNhanGiaiThuong(NhanGTEntity nhanGT) {
        nhanGTRepository.save(nhanGT);
    }

    @Transactional
    public void updateAwardWithScientists(GiaithuongEntity award, List<Integer> nkhIds) {
        // Save the award first
        giaiThuongRepository.save(award);
        // Delete existing scientist associations
        nhanGTRepository.deleteByGtId(award.getId());
        // Add new associations
        if (nkhIds != null) {
            for (Integer nkhId : nkhIds) {
                NhanGTEntity nhanGT = new NhanGTEntity(nkhId, award.getId());
                nhanGTRepository.save(nhanGT);
            }
        }
    }
    
    public Map<String, Object> getThongKeGiaiThuong() {
        List<Object[]> results = giaiThuongRepository.thongKeTheoNoiDung();
        List<Map<String, Object>> thongKeList = new ArrayList<>();
        long total = 0;

        for (Object[] result : results) {
            GiaithuongEntity.NoiDung noiDung = (GiaithuongEntity.NoiDung) result[0];
            Long count = (Long) result[1];
            Map<String, Object> stat = new HashMap<>();
            stat.put("noiDung", noiDung != null ? noiDung : "Không xác định");
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