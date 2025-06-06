package Nhom2.DoAnWeb.NTUScientistInfor.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Nhom2.DoAnWeb.NTUScientistInfor.model.TaiKhoanEntity;
import Nhom2.DoAnWeb.NTUScientistInfor.repository.TaikhoanReponsitory;

@Service
public class TaiKhoanService {
	@Autowired
    private TaikhoanReponsitory taikhoanRepository;
    
    public List<TaiKhoanEntity> getAllTaiKhoan() {
        return taikhoanRepository.findAll();
    }

    public TaiKhoanEntity findByTenTaiKhoan(String tenTaiKhoan) {
        return taikhoanRepository.findById(tenTaiKhoan).orElse(null);
    }
    
    public void deleteTaiKhoan(String tenTaiKhoan) {
        taikhoanRepository.deleteById(tenTaiKhoan);
    }
}
