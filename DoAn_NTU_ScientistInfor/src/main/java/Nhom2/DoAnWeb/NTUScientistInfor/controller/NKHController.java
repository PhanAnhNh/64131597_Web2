package Nhom2.DoAnWeb.NTUScientistInfor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import Nhom2.DoAnWeb.NTUScientistInfor.model.NhaKhoaHoc;
import Nhom2.DoAnWeb.NTUScientistInfor.service.NhaKhoaHocService;
import Nhom2.DoAnWeb.NTUScientistInfor.service.TaiKhoanService;

@Controller
@RequestMapping("/quanlykhoahoc")
public class NKHController {
	
	@Autowired
    private NhaKhoaHocService nkhService;

    @Autowired
    private TaiKhoanService taiKhoanService;


    @GetMapping("/nhakhoahoc")
    public String getAllNKH(Model model) {
        model.addAttribute("nkhList", nkhService.getAllNhaKhoaHoc());
        return "nkh-list";
    }
    
    @GetMapping("/nkh/add")
    public String addForm(Model model) {
        model.addAttribute("nkh", new NhaKhoaHoc());
        model.addAttribute("taiKhoanList", taiKhoanService.getAllTaiKhoan());
        return "nkh-new";
    }

    @PostMapping("/nhakhoahoc/add")
    public String addNKH(@ModelAttribute("nkh") NhaKhoaHoc nkh, 
                         @RequestParam("hinhAnhFile") MultipartFile hinhAnhFile, 
                         RedirectAttributes redirectAttributes) {
        try {
            nkhService.addNKH(nkh, hinhAnhFile);
            redirectAttributes.addFlashAttribute("successMessage", "Thêm nhà khoa học thành công.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi thêm nhà khoa học: " + e.getMessage());
        }
        return "redirect:/quanlykhoahoc/nhakhoahoc";
    }

    @GetMapping("/nkh/edit/{nkhId}")
    public String editForm(@PathVariable int nkhId, Model model, RedirectAttributes redirectAttributes) {
        NhaKhoaHoc nkh = nkhService.findById(nkhId);
        if (nkh == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Nhà khoa học không tồn tại.");
            return "redirect:/quanlykhoahoc/nhakhoahoc";
        }
        model.addAttribute("nkh", nkh);
        model.addAttribute("taiKhoanList", taiKhoanService.getAllTaiKhoan());
        return "nkhEdit";
    }

    @PostMapping("/nkh/edit/{nkhId}")
    public String editNKH(@PathVariable int nkhId, @ModelAttribute("nkh") NhaKhoaHoc updatedNkh, 
                          @RequestParam("hinhAnhFile") MultipartFile hinhAnhFile, RedirectAttributes redirectAttributes) {
        try {
            nkhService.updateNKH(nkhId, updatedNkh, hinhAnhFile);
            redirectAttributes.addFlashAttribute("successMessage", "Cập nhật nhà khoa học thành công.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi cập nhật nhà khoa học: " + e.getMessage());
        }
        return "redirect:/quanlykhoahoc/nhakhoahoc";
    }

    

    @GetMapping("/nkh/search")
    public String searchNKH(@RequestParam(value = "keyword", required = false) String keyword,
                            @RequestParam(value = "hocVi", required = false) String hocVi, Model model) {
        model.addAttribute("nkhList", nkhService.searchNKH(keyword, hocVi));
        model.addAttribute("keyword", keyword);
        model.addAttribute("hocVi", hocVi);
        return "nkh-list";
    }

    @GetMapping("/nkh/{nkhId}/detail")
    public String getNkhDetail(@PathVariable Integer nkhId, Model model, RedirectAttributes redirectAttributes) {
        try {
            NhaKhoaHoc nkh = nkhService.getNhaKhoaHocById(nkhId);
            if (nkh == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "Nhà khoa học không tồn tại.");
                return "redirect:/quanlykhoahoc/nhakhoahoc";
            }
            long tongSoBao = nkhService.getTongBaiBaoByNkhId(nkhId);
            long tongSoSach = nkhService.getTongSoSachByNkhId(nkhId);
            long tongSoDeTai = nkhService.getTongSoDeTaiByNkhId(nkhId);
            model.addAttribute("nkh", nkh);
            model.addAttribute("tongSoBao", tongSoBao);
            model.addAttribute("tongSoSach", tongSoSach);
            model.addAttribute("tongSoDeTai", tongSoDeTai);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi lấy thông tin nhà khoa học: " + e.getMessage());
            return "redirect:/quanlykhoahoc/nhakhoahoc";
        }
        return "nkh-detail";
    }
    
    //Xóa nhà khoa học
    @GetMapping("/nkh/delete/{nkhId}")
    public String deleteNKH(@PathVariable int nkhId, RedirectAttributes redirectAttributes) {
        try {
            nkhService.deleteNKH(nkhId);
            redirectAttributes.addFlashAttribute("successMessage", "Xóa nhà khoa học thành công.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi xóa nhà khoa học: " + e.getMessage());
        }
        return "redirect:/quanlykhoahoc/nhakhoahoc";
    }
}