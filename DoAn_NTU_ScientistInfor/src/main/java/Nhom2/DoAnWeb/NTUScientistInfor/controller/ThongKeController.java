package Nhom2.DoAnWeb.NTUScientistInfor.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import Nhom2.DoAnWeb.NTUScientistInfor.repository.CongtrinhRepository;
import Nhom2.DoAnWeb.NTUScientistInfor.service.BaiBaoService;
import Nhom2.DoAnWeb.NTUScientistInfor.service.CongTrinhService;
import Nhom2.DoAnWeb.NTUScientistInfor.service.DeTaiKHService;
import Nhom2.DoAnWeb.NTUScientistInfor.service.GiaiThuongService;
import Nhom2.DoAnWeb.NTUScientistInfor.service.NhaKhoaHocService;
import Nhom2.DoAnWeb.NTUScientistInfor.service.SHTTService;
import Nhom2.DoAnWeb.NTUScientistInfor.service.SachService;

@Controller
@RequestMapping("/quanlykhoahoc")
public class ThongKeController {
	
	@Autowired
    private BaiBaoService baiBaoService;
	
	 @Autowired
	private NhaKhoaHocService nhaKhoaHocService;
	 
	 @Autowired
	private DeTaiKHService deTaiKHService;
	 
	 @Autowired
	 private SachService sachService;
	 
	 @Autowired
	 private CongTrinhService congTrinhService;
	 
	 @Autowired
	 private GiaiThuongService giaiThuongService;
	 
	 @Autowired
	 private SHTTService shttService;
	    
    
    @GetMapping("/thongke")
    public String thongKeBaiBao(Model model) {
        Map<String, Object> thongKe = baiBaoService.getThongKeBaiBao();
        model.addAllAttributes(thongKe);
        return "thongKeBaiBao";
    }
    
    @GetMapping("/thong-ke-nha-khoa-hoc")
    public String thongKeNKH(Model model) {
        List<Map<String, Object>> thongKeHocVi = nhaKhoaHocService.getThongKeTheoHocVi();
        model.addAttribute("thongKeHocVi", thongKeHocVi);
        return "thongkenhakhoahoc";
    }
    
    @GetMapping("/thong-ke-de-tai")
    public String thongKeDeTai(Model model) {
        List<Map<String, Object>> thongKeDeTai = deTaiKHService.getThongKeDeTai();
        model.addAttribute("thongKeDeTai", thongKeDeTai);
        return "thongkeDeTai";
    }
    
    @GetMapping("/thong-ke-giao-trinh")
    public String thongKeSach(Model model) {
    	Map<String, Object> thongKeSach = sachService.getThongKeSach();
        model.addAttribute("thongKeSach", thongKeSach.get("counts"));
        model.addAttribute("totalSach", thongKeSach.get("total"));
        return "thongkeGT";
    }
    
    @GetMapping("/thong-ke-cong-trinh")
    public String thongKeCT(Model model) {
    	Map<String, Object> thongKeCongTrinh = congTrinhService.getThongKeCongTrinh();
        model.addAttribute("thongKeCongTrinh", thongKeCongTrinh.get("thongKe"));
        model.addAttribute("totalCongTrinh", thongKeCongTrinh.get("total"));
        return "thongkeCT";
    }
    
    @GetMapping("/thong-ke-giai-thuong")
    public String thongKeGiaiThuong(Model model) {
    	Map<String, Object> thongKeGiaiThuong = giaiThuongService.getThongKeGiaiThuong();
        model.addAttribute("thongKeGiaiThuong", thongKeGiaiThuong.get("thongKe"));
        model.addAttribute("totalGiaiThuong", thongKeGiaiThuong.get("total"));
        return "thongkegiaithuong";
    }
    
    @GetMapping("/thong-ke-so-huu-tri-tue")
    public String thongKeSHTT(Model model) {
    	Map<String, Object> thongKeSHTT = shttService.getThongKeSHTT();
        model.addAttribute("thongKeSHTT", thongKeSHTT.get("thongKe"));
        model.addAttribute("totalSHTT", thongKeSHTT.get("total"));
        return "thongkesohuutritue";
    }

}
