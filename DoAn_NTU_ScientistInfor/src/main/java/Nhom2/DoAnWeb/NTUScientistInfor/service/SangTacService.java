package Nhom2.DoAnWeb.NTUScientistInfor.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Nhom2.DoAnWeb.NTUScientistInfor.model.NhaKhoaHoc;
import Nhom2.DoAnWeb.NTUScientistInfor.model.SangTacEntity;
import Nhom2.DoAnWeb.NTUScientistInfor.repository.NhaKhoaHocReponsitory;
import Nhom2.DoAnWeb.NTUScientistInfor.repository.SangTacRepository;

@Service
public class SangTacService {
	@Autowired
    private SangTacRepository sangTacRepository;
	
	@Autowired
    private NhaKhoaHocReponsitory nhaKhoaHocReponsitory;

    public List<SangTacEntity> findTacGiaByBaiBaoId(String baiBaoId) {
        return sangTacRepository.findByBaiBaoId(baiBaoId);
    }
    
    public List<NhaKhoaHoc> findAllNhaKhoaHoc() {
        return nhaKhoaHocReponsitory.findAll();
    }

    

}
