package Nhom2.DoAnWeb.NTUScientistInfor.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import Nhom2.DoAnWeb.NTUScientistInfor.model.BaiBaoKHEntity;
import Nhom2.DoAnWeb.NTUScientistInfor.model.NhaKhoaHoc;
import Nhom2.DoAnWeb.NTUScientistInfor.model.SangTacEntity;
import Nhom2.DoAnWeb.NTUScientistInfor.model.tapchiKHHTKHEntity;
import Nhom2.DoAnWeb.NTUScientistInfor.repository.NhaKhoaHocReponsitory;
import Nhom2.DoAnWeb.NTUScientistInfor.repository.TapchiKHReponsitory;
import Nhom2.DoAnWeb.NTUScientistInfor.service.BaiBaoService;
import Nhom2.DoAnWeb.NTUScientistInfor.service.SangTacService;

@Controller
@RequestMapping("/quanlykhoahoc")
public class BaiBaoController {
	
	@Autowired
    private BaiBaoService baiBaoKHService;
	
	@Autowired
	private SangTacService sangTacService;
	
	@Autowired
    private NhaKhoaHocReponsitory nhaKhoaHocRepository;
	
	@Autowired
    private TapchiKHReponsitory tapchiKHReponsitory;
	
	@GetMapping("/trangchu")
    public String getTrangChu(Model model) {
        return "trangchu"; // tên file HTML trong thư mục templates
    }
	
	@GetMapping("/baibaokhoahoc")
    public String danhSachBaiBao(Model model, 
                               @RequestParam(value = "keyword", required = false) String keyword,
                               @RequestParam(value = "nkhId", required = false) Integer nkhId) {
        List<BaiBaoKHEntity> danhSachBaiBao = baiBaoKHService.searchBaiBao(keyword, nkhId);
        
        Map<String, List<String>> tacGiaMap = new HashMap<>();
        for (BaiBaoKHEntity baiBao : danhSachBaiBao) {
            List<SangTacEntity> sangTacList = sangTacService.findTacGiaByBaiBaoId(baiBao.getBaiBaoId());
            List<String> tenTacGia = sangTacList.stream()
                    .map(st -> st.getNhaKhoaHoc().getHoTen())
                    .toList();
            tacGiaMap.put(baiBao.getBaiBaoId(), tenTacGia);
        }
        
        model.addAttribute("tacGiaMap", tacGiaMap);
        model.addAttribute("danhSachBaiBao", danhSachBaiBao);
        model.addAttribute("danhSachNhaKhoaHoc", sangTacService.findAllNhaKhoaHoc());
        model.addAttribute("keyword", keyword);
        model.addAttribute("selectedNkhId", nkhId);
        return "baibaokhoahoc";
    }
	

	    // Lấy bài báo khoa học theo ID
	    @GetMapping("/{baiBaoId}")
	    public BaiBaoKHEntity getBaiBaoKHById(@PathVariable String baiBaoId) {
	        return baiBaoKHService.getBaiBaoKHById(baiBaoId);
	    }
	    
	 // Hiển thị form thêm bài báo khoa học
	    @GetMapping("/baibaokhoahoc/them-baibao")
	    public String hienThiFormThemBaiBao(Model model) {
	    	BaiBaoKHEntity baiBao = new BaiBaoKHEntity();
	    	
	    	baiBao.setBaiBaoId(baiBaoKHService.generateBaiBaoId());
	    	
	        model.addAttribute("baiBao", baiBao);
	        model.addAttribute("danhSachNhaKhoaHoc", sangTacService.findAllNhaKhoaHoc());
	        return "thembaibao"; // tên file HTML form thêm bài báo
	    }
	    
	    
	 // Xử lý thêm bài báo mới
	    @PostMapping("/baibaokhoahoc/them-baibao")
	    public String themBaiBao(@ModelAttribute("baiBao") BaiBaoKHEntity baiBao,
	                            @RequestParam(value = "tacGiaIds", required = false) List<Integer> tacGiaIds,
	                            BindingResult result,
	                            RedirectAttributes redirectAttributes) {
	        if (result.hasErrors()) {
	            return "thembaibao";
	        }
	        
	        try {
	            BaiBaoKHEntity savedBaiBao = baiBaoKHService.addBaiBao(baiBao, tacGiaIds);
	            redirectAttributes.addFlashAttribute("successMessage", "Thêm bài báo thành công!");
	            return "redirect:/quanlykhoahoc/baibaokhoahoc";
	        } catch (Exception e) {
	            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi thêm bài báo: " + e.getMessage());
	            return "redirect:/quanlykhoahoc/baibaokhoahoc/them-baibao";
	        }
	    }
	    
	    
	 // Hiển thị form sửa bài báo khoa học
	    @GetMapping("/baibaokhoahoc/sua/{id}")
	    public String hienThiFormSuaBaiBao(@PathVariable("id") String baiBaoId, Model model) {
	        BaiBaoKHEntity baiBao = baiBaoKHService.getBaiBaoKHById(baiBaoId);
	        model.addAttribute("baiBao", baiBao);
	        model.addAttribute("danhSachNhaKhoaHoc", sangTacService.findAllNhaKhoaHoc());

	        // Load các tác giả hiện tại
	        List<SangTacEntity> sangTacList = sangTacService.findTacGiaByBaiBaoId(baiBaoId);
	        List<Integer> tacGiaIds = sangTacList.stream()
	                .map(SangTacEntity::getNkhId)
	                .toList();
	        baiBao.setTacGiaIds(tacGiaIds);

	        return "suabaibao"; // trỏ tới file HTML sửa bài báo
	    }

	    // Xử lý submit form sửa bài báo
	    @PostMapping("/baibaokhoahoc/capnhat")
	    public String capNhatBaiBao(@ModelAttribute("baiBao") BaiBaoKHEntity baiBao,
	                               @RequestParam(value = "tacGiaIds", required = false) List<Integer> tacGiaIds,
	                               BindingResult result,
	                               RedirectAttributes redirectAttributes) {
	        if (result.hasErrors()) {
	            return "suabaibao";
	        }
	        
	        try {
	            baiBaoKHService.updateBaiBao(baiBao, tacGiaIds);
	            redirectAttributes.addFlashAttribute("successMessage", "Cập nhật bài báo thành công!");
	        } catch (Exception e) {
	            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi cập nhật bài báo: " + e.getMessage());
	        }
	        return "redirect:/quanlykhoahoc/baibaokhoahoc";
	    }
	   

	  //Xóa bài báo
	    
	    @PostMapping("/baibaokhoahoc/xoa/{id}")
	    public String xoaBaiBao(@PathVariable("id") String baiBaoId, RedirectAttributes redirectAttributes) {
	        try {
	            baiBaoKHService.deleteBaiBao(baiBaoId);
	            redirectAttributes.addFlashAttribute("successMessage", "Xóa bài báo thành công!");
	        } catch (Exception e) {
	            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi xóa bài báo: " + e.getMessage());
	        }
	        return "redirect:/quanlykhoahoc/baibaokhoahoc";
	    }
	    
	    //Xem chi tiêt

	    @GetMapping("/baibaokhoahoc/chitiet/{id}")
	    public String xemChiTietBaiBao(@PathVariable("id") String baiBaoId, Model model) {
	        BaiBaoKHEntity baiBao = baiBaoKHService.getBaiBaoKHById(baiBaoId);
	        if (baiBao == null) {
	            return "redirect:/quanlykhoahoc/baibaokhoahoc";
	        }

	        tapchiKHHTKHEntity tapChi = baiBaoKHService.getTapChiById(baiBao.getTapChiId()).orElse(null);
	        if (tapChi == null) {
	            tapChi = new tapchiKHHTKHEntity(); // Provide a default object to avoid null issues
	            tapChi.setTapChiId(baiBao.getTapChiId()); // Set the ID to match the article
	        }

	        List<SangTacEntity> sangTacList = sangTacService.findTacGiaByBaiBaoId(baiBaoId);
	        List<String> tenTacGiaList = sangTacList.stream()
	                .map(st -> st.getNhaKhoaHoc().getHoTen())
	                .collect(Collectors.toList());
	        String tenTacGia = tenTacGiaList.isEmpty() ? "Không có tác giả" : String.join(", ", tenTacGiaList);

	        model.addAttribute("baiBao", baiBao);
	        model.addAttribute("tapChi", tapChi);
	        model.addAttribute("tenTacGia", tenTacGia);

	        return "chitietBBKH";
	    }
	    
	    
	    @PostMapping("/baibaokhoahoc/chitiet/capnhat-tapchi/{baiBaoId}")
	    public String capNhatTapChiInline(@PathVariable("baiBaoId") String baiBaoId,
	                                      @ModelAttribute("tapChi") tapchiKHHTKHEntity tapChi,
	                                      RedirectAttributes redirectAttributes) {
	        try {
	            baiBaoKHService.updateTapChi(tapChi);
	            redirectAttributes.addFlashAttribute("successMessage", "Cập nhật tạp chí thành công!");
	        } catch (Exception e) {
	            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi cập nhật tạp chí: " + e.getMessage());
	        }
	        return "redirect:/quanlykhoahoc/baibaokhoahoc/chitiet/" + baiBaoId;
	    }
 
}
