package Nhom2.DoAnWeb.NTUScientistInfor.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Nhom2.DoAnWeb.NTUScientistInfor.model.CongtrinhEntity;
import Nhom2.DoAnWeb.NTUScientistInfor.model.NhaKhoaHoc;
import Nhom2.DoAnWeb.NTUScientistInfor.repository.CongtrinhRepository;
import Nhom2.DoAnWeb.NTUScientistInfor.repository.NhaKhoaHocReponsitory;
import jakarta.transaction.Transactional;

@Service
public class CongTrinhService {
	@Autowired
	private CongtrinhRepository congTrinhRepository;
	
	@Autowired
	private NhaKhoaHocReponsitory nkhRepository;
	// chức năng hiện thị danh sách công trình
	 public List<CongtrinhEntity> getAllCongTrinh() {
	        return congTrinhRepository.getAllWithThamGia();
	 }
	 // chức năng thêm 1 công trình
	        public Optional<CongtrinhEntity> getCongTrinhById(String id) {
	            return congTrinhRepository.findById(id);
	        }

	        @Transactional
	        public CongtrinhEntity saveCongTrinh(CongtrinhEntity congTrinh, List<Integer> nkhIds) {
	            if (congTrinhRepository.existsById(congTrinh.getCongTrinhID())) {
	                throw new IllegalArgumentException("Công trình với ID " + congTrinh.getCongTrinhID() + " đã tồn tại.");
	            }

	            List<NhaKhoaHoc> thamGia = new ArrayList<>();
	            if (nkhIds != null && !nkhIds.isEmpty()) {
	                for (Integer nkhId : nkhIds) {
	                    Optional<NhaKhoaHoc> optionalNkh = nkhRepository.findById(nkhId);
	                    if (optionalNkh.isPresent()) {
	                        NhaKhoaHoc nkh = optionalNkh.get();
	                        nkh.getNkh_thamgiacongtrinh().add(congTrinh); 
	                        thamGia.add(nkh);
	                        nkhRepository.save(nkh); 
	                    }
	                }
	            }

	            congTrinh.setThamGia(thamGia);
	            return congTrinhRepository.save(congTrinh);
	        } 
	        
	        public List<NhaKhoaHoc> getAllNKH() {
	            return nkhRepository.findAll();
	        }
	        // xử lí chức năng edit
	        @Transactional
	        public CongtrinhEntity updateCongTrinh(CongtrinhEntity congTrinh, List<Integer> nkhIds) {
	           
	           
	            CongtrinhEntity existingCongTrinh = congTrinhRepository.findById(congTrinh.getCongTrinhID()).get();

	          
	            existingCongTrinh.setTenCongTrinh(congTrinh.getTenCongTrinh());
	            existingCongTrinh.setHinhThuc(congTrinh.getHinhThuc());
	            existingCongTrinh.setQuyMo(congTrinh.getQuyMo());
	            existingCongTrinh.setNoiApDung(congTrinh.getNoiApDung());
	            existingCongTrinh.setNamBatDau(congTrinh.getNamBatDau());
	            existingCongTrinh.setNamKetThuc(congTrinh.getNamKetThuc());
	            

	          
	            List<NhaKhoaHoc> dangThamGia = existingCongTrinh.getThamGia();
	            
	            for (NhaKhoaHoc nkh : dangThamGia) {
	                nkh.getNkh_thamgiacongtrinh().remove(existingCongTrinh);
	                nkhRepository.save(nkh);
	            }
	            dangThamGia.clear();

	          
	            List<NhaKhoaHoc> newThamGia = new ArrayList<>();
	            if (nkhIds != null && !nkhIds.isEmpty()) {
	                for (Integer nkhId : nkhIds) {
	                    Optional<NhaKhoaHoc> optionalNkh = nkhRepository.findById(nkhId);
	                    if (optionalNkh.isPresent()) {
	                        NhaKhoaHoc nkh = optionalNkh.get();
	                        nkh.getNkh_thamgiacongtrinh().add(existingCongTrinh);
	                        newThamGia.add(nkh);
	                        nkhRepository.save(nkh);
	                    }
	                }
	            }
	            existingCongTrinh.setThamGia(newThamGia);

	            return congTrinhRepository.save(existingCongTrinh);
	        }
	        //hàm xử lí chức năng tìm kiếm
	        public List<CongtrinhEntity> timKiemCongTrinh(String keyword) {
	            if (keyword == null || keyword.trim().isEmpty()) {
	                return congTrinhRepository.findAll();
	            }
	            return congTrinhRepository.searchByKeyword(keyword);
	        }

	        public List<CongtrinhEntity> getAllProjects() {
	            return congTrinhRepository.findAll();
	        }
	        
	        // hàm xử lí chức năng xóa
	        @Transactional
	        public void deleteCongTrinh(String id) {
	            Optional<CongtrinhEntity> ct = congTrinhRepository.findById(id);
	            if (!ct.isPresent()) {
	                throw new IllegalArgumentException("Công trinh với ID " + id + " không tồn tại.");
	            }

	            CongtrinhEntity congTrinh = ct.get();
	            List<NhaKhoaHoc> thamGia = congTrinh.getThamGia();
	            
	            if (thamGia != null) {
	                for (NhaKhoaHoc nkh : thamGia) {
	                    nkh.getNkh_thamgiacongtrinh().remove(congTrinh);
	                    nkhRepository.save(nkh);
	                }
	                congTrinh.getThamGia().clear();
	            }

	            congTrinhRepository.delete(congTrinh);
	        
	        }
	        
	        public Map<String, Object> getThongKeCongTrinh() {
	            List<Object[]> results = congTrinhRepository.thongKeTheoQuyMo();
	            List<Map<String, Object>> thongKeList = new ArrayList<>();
	            long total = 0;

	            for (Object[] result : results) {
	                String quyMo = (String) result[0];
	                Long count = (Long) result[1];
	                Map<String, Object> stat = new HashMap<>();
	                stat.put("quyMo", quyMo != null ? quyMo : "Không xác định");
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
