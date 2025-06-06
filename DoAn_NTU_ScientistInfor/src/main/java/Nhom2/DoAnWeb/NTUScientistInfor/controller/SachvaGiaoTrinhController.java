package Nhom2.DoAnWeb.NTUScientistInfor.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import Nhom2.DoAnWeb.NTUScientistInfor.model.SachEntity;
import Nhom2.DoAnWeb.NTUScientistInfor.model.TacGiaSachEntity;
import Nhom2.DoAnWeb.NTUScientistInfor.service.NhaKhoaHocService;
import Nhom2.DoAnWeb.NTUScientistInfor.service.SachService;

@Controller
@RequestMapping("/quanlykhoahoc")
public class SachvaGiaoTrinhController {
	
	@Autowired
    private SachService sachService;

    @Autowired
    private NhaKhoaHocService nhaKhoaHocService;
    
    
	@GetMapping("/giaotrinh")
    public String danhSachSach(Model model,
                              @RequestParam(value = "keyword", required = false) String keyword,
                              @RequestParam(value = "nkhId", required = false) Integer nkhId,
                              @RequestParam(value = "loaiSach", required = false) SachEntity.LoaiSach loaiSach) {
        // Lấy danh sách sách theo tiêu chí tìm kiếm
        List<SachEntity> danhSachSach = sachService.searchSach(keyword, nkhId, loaiSach);

        // Tạo map chứa danh sách tác giả cho mỗi sách
        Map<String, List<String>> tacGiaMap = new HashMap<>();
        for (SachEntity sach : danhSachSach) {
            List<TacGiaSachEntity> tacGiaList = sachService.findTacGiaBySachId(sach.getId());
            List<String> tenTacGia = tacGiaList.stream()
                    .map(tg -> tg.getNkhId())
                    .map(id -> nhaKhoaHocService.getNhaKhoaHocById(id).getHoTen())
                    .collect(Collectors.toList());
            tacGiaMap.put(sach.getId(), tenTacGia);
        }

        // Thêm các thuộc tính vào model
        model.addAttribute("danhSachSach", danhSachSach);
        model.addAttribute("tacGiaMap", tacGiaMap);
        model.addAttribute("danhSachNhaKhoaHoc", nhaKhoaHocService.getAllNhaKhoaHoc());
        model.addAttribute("loaiSachList", SachEntity.LoaiSach.values());
        model.addAttribute("keyword", keyword);
        model.addAttribute("selectedNkhId", nkhId);
        model.addAttribute("selectedLoaiSach", loaiSach);

        return "sachvagiaotrinh";
    }
	
	//Hiển thị trang thêm sách và giáo trình
	@GetMapping("/giaotrinh/them-sach")
    public String hienThiFormThemSach(Model model) {
        SachEntity sach = new SachEntity();
        // Set default LoaiSach to generate initial ID
        sach.setLoaiSach(SachEntity.LoaiSach.Giáo_trình);
        sach.setId(sachService.generateSachId(sach.getLoaiSach()));
        model.addAttribute("sach", sach);
        model.addAttribute("danhSachNhaKhoaHoc", nhaKhoaHocService.getAllNhaKhoaHoc());
        model.addAttribute("loaiSachList", SachEntity.LoaiSach.values());
        model.addAttribute("nhaXuatBanList", SachEntity.NhaXuatBan.values());
        return "themSachvaGiaotrinh";
    }

	
	@PostMapping("/giaotrinh/them-sach")
    public String themSach(@ModelAttribute("sach") SachEntity sach,
                          @RequestParam(value = "tacGiaIds", required = false) List<Integer> tacGiaIds,
                          BindingResult result,
                          RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "themsach";
        }
        try {
            if (sach.getLoaiSach() == null) {
                throw new IllegalArgumentException("Loại sách không được để trống");
            }
            if (sach.getNhaXuatBan() == null) {
                throw new IllegalArgumentException("Nhà xuất bản không được để trống");
            }
            SachEntity savedSach = sachService.addSach(sach, tacGiaIds);
            redirectAttributes.addFlashAttribute("successMessage", "Thêm sách thành công!");
            return "redirect:/quanlykhoahoc/giaotrinh";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi thêm sách: " + e.getMessage());
            return "redirect:/quanlykhoahoc/giaotrinh/them-sach";
        }
    }
	
	@GetMapping("/giaotrinh/generate-sach-id")
    @ResponseBody
    public String generateSachId(@RequestParam("loaiSach") SachEntity.LoaiSach loaiSach) {
        return sachService.generateSachId(loaiSach);
    }
	
	//Xóa sách và giáo trình
	@PostMapping("/giaotrinh/xoa-sach/{id}")
	public String xoaSach(@PathVariable("id") String sachId, RedirectAttributes redirectAttributes) {
	    try {
	        boolean deleted = sachService.deleteSach(sachId);
	        if (deleted) {
	            redirectAttributes.addFlashAttribute("successMessage", "Xóa sách thành công!");
	        } else {
	            redirectAttributes.addFlashAttribute("errorMessage", "Không thể xóa sách.");
	        }
	    } catch (Exception e) {
	        redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi xóa sách: " + e.getMessage());
	    }
	    return "redirect:/quanlykhoahoc/giaotrinh";
	}
	// Hiển thị form sửa sách và giáo trình
		@GetMapping("/giaotrinh/sua-sach-giao-trinh")
		public String hienThiFormSuaSach(@RequestParam("id") String sachId, Model model) {
		    SachEntity sach = sachService.getAllSach().stream()
		            .filter(s -> s.getId().equals(sachId))
		            .findFirst()
		            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sách không tồn tại với ID: " + sachId));
	
		    // Fetch the list of author IDs for the book
		    List<TacGiaSachEntity> tacGiaList = sachService.findTacGiaBySachId(sachId);
		    List<Integer> tacGiaIds = tacGiaList.stream()
		            .map(TacGiaSachEntity::getNkhId)
		            .collect(Collectors.toList());
	
		    model.addAttribute("sach", sach);
		    model.addAttribute("oldId", sachId);
		    model.addAttribute("danhSachNhaKhoaHoc", nhaKhoaHocService.getAllNhaKhoaHoc());
		    model.addAttribute("loaiSachList", SachEntity.LoaiSach.values());
		    model.addAttribute("nhaXuatBanList", SachEntity.NhaXuatBan.values());
		    model.addAttribute("tacGiaIds", tacGiaIds); // Add tacGiaIds to the model
		    return "suaSachvaGiaotrinh";
		}

		@PostMapping("/giaotrinh/sua-sach-giao-trinh")
		public String suaSach(@ModelAttribute("sach") SachEntity sach,
		                      @RequestParam(value = "tacGiaIds", required = false) List<Integer> tacGiaIds,
		                      @RequestParam("oldId") String oldId,
		                      BindingResult result,
		                      RedirectAttributes redirectAttributes,
		                      Model model) {
		    if (result.hasErrors()) {
		        // Repopulate the model for the form
		        model.addAttribute("oldId", oldId);
		        model.addAttribute("danhSachNhaKhoaHoc", nhaKhoaHocService.getAllNhaKhoaHoc());
		        model.addAttribute("loaiSachList", SachEntity.LoaiSach.values());
		        model.addAttribute("nhaXuatBanList", SachEntity.NhaXuatBan.values());
		        model.addAttribute("tacGiaIds", tacGiaIds != null ? tacGiaIds : new ArrayList<>());
		        return "suaSachvaGiaotrinh";
		    }
		    try {
		        if (sach.getLoaiSach() == null) {
		            throw new IllegalArgumentException("Loại sách không được để trống");
		        }
		        if (sach.getNhaXuatBan() == null) {
		            throw new IllegalArgumentException("Nhà xuất bản không được để trống");
		        }
		        SachEntity updatedSach = sachService.updateSach(oldId, sach, tacGiaIds);
		        redirectAttributes.addFlashAttribute("successMessage", "Cập nhật sách thành công!");
		        return "redirect:/quanlykhoahoc/giaotrinh";
		    } catch (Exception e) {
		        redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi cập nhật sách: " + e.getMessage());
		        return "redirect:/quanlykhoahoc/giaotrinh/sua-sach-giao-trinh?id=" + oldId;
		    }
		}
}