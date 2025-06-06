package Nhom2.DoAnWeb.NTUScientistInfor.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import Nhom2.DoAnWeb.NTUScientistInfor.model.DetaiKHEntitty;
import Nhom2.DoAnWeb.NTUScientistInfor.model.DetaiKHEntitty.CapDeTai;
import Nhom2.DoAnWeb.NTUScientistInfor.service.DeTaiKHService;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;

@Controller
@RequestMapping("/quanlykhoahoc")
public class DeTaiController {
    @Autowired
    private DeTaiKHService dtService;

    @GetMapping("/detai")
    public String getAllDeTaiKH(Model model) {
        model.addAttribute("deTaiKHList", dtService.getAllDeTai());
        model.addAttribute("capDeTaiValues", CapDeTai.values());
        return "deTaiList";
    }

    @GetMapping("/detai/add")
    public String showAddForm(Model model) {
        model.addAttribute("deTaiKH", new DetaiKHEntitty());
        model.addAttribute("nkhList", dtService.getAllNKH());
        return "create-detai";
    }

    @PostMapping("/detai/add")
    public String addDeTaiKH(@Valid @ModelAttribute("deTaiKH") DetaiKHEntitty deTaiKH,
                             BindingResult result,
                             @RequestParam(value = "nkhIds", required = false) List<Integer> nkhIds,
                             Model model,
                             RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("nkhList", dtService.getAllNKH());
            return "create-detai";
        }
        try {
            dtService.saveDeTaiKH(deTaiKH, nkhIds);
            redirectAttributes.addFlashAttribute("successMessage", "Thêm đề tài thành công!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            model.addAttribute("nkhList", dtService.getAllNKH());
            return "create-detai";
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi dữ liệu: " + e.getMessage());
            model.addAttribute("nkhList", dtService.getAllNKH());
            return "create-detai";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi thêm đề tài: " + e.getMessage());
            model.addAttribute("nkhList", dtService.getAllNKH());
            return "create-detai";
        }
        return "redirect:/quanlykhoahoc/detai";
    }

    @GetMapping("/detai/edit/{id}")
    public String showEditForm(@PathVariable("id") String id, Model model) {
        DetaiKHEntitty deTaiKH = dtService.getDeTaiById(id).orElse(null);
        if (deTaiKH != null) {
            model.addAttribute("deTaiKH", deTaiKH);
            model.addAttribute("nkhList", dtService.getAllNKH());
            return "edit_detail";
        } else {
            return "redirect:/quanlykhoahoc/detai";
        }
    }

    @PostMapping("/detai/edit/{id}")
    public String updateDeTaiKH(@PathVariable("id") String id,
                                @Valid @ModelAttribute("deTaiKH") DetaiKHEntitty deTaiKH,
                                BindingResult result,
                                @RequestParam(value = "nkhIds", required = false) List<Integer> nkhIds,
                                Model model,
                                RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("nkhList", dtService.getAllNKH());
            return "edit_detail";
        }
        try {
            deTaiKH.setDeTaiId(id);
            dtService.updateDeTaiKH(deTaiKH, nkhIds);
            redirectAttributes.addFlashAttribute("successMessage", "Cập nhật đề tài thành công!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            model.addAttribute("nkhList", dtService.getAllNKH());
            return "edit_detail";
        }  // Trong DeTaiController.java, phương thức updateDeTaiKH
        catch (DataIntegrityViolationException e) {
            String errorMessage = "Lỗi dữ liệu: ";
            if (e.getCause() instanceof ConstraintViolationException && e.getMessage().contains("Duplicate entry")) {
                errorMessage += "Nhà khoa học đã được thêm vào đề tài này. Vui lòng kiểm tra lại danh sách nhà khoa học.";
            } else {
                errorMessage += e.getMessage();
            }
            redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
            model.addAttribute("nkhList", dtService.getAllNKH());
            return "edit_detail";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi cập nhật đề tài: " + e.getMessage());
            model.addAttribute("nkhList", dtService.getAllNKH());
            return "edit_detail";
        }
        return "redirect:/quanlykhoahoc/detai";
    }

    @GetMapping("/detai/view/{id}")
    public String showViewForm(@PathVariable("id") String id, Model model, RedirectAttributes redirectAttributes) {
        DetaiKHEntitty deTaiKH = dtService.getDeTaiById(id).orElse(null);
        if (deTaiKH != null) {
            model.addAttribute("deTaiKH", deTaiKH);
            model.addAttribute("nkhList", dtService.getAllNKH());
            return "view_detai";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Đề tài với ID " + id + " không tồn tại.");
            return "redirect:/quanlykhoahoc/detai";
        }
    }

    @GetMapping("/detai/search")
    public String listProjects(@RequestParam(value = "keyword", required = false) String keyword,
                              @RequestParam(value = "capDeTai", required = false) CapDeTai capDeTai,
                              Model model) {
        List<DetaiKHEntitty> deTaiKHList = dtService.searchProjects(keyword, capDeTai);
        model.addAttribute("deTaiKHList", deTaiKHList);
        model.addAttribute("keyword", keyword);
        model.addAttribute("capDeTai", capDeTai);
        model.addAttribute("capDeTaiValues", CapDeTai.values());
        return "deTaiList";
    }

    @GetMapping("/detai/delete/{id}")
    public String deleteProject(@PathVariable("id") String id, RedirectAttributes redirectAttributes) {
        try {
            dtService.deleteDeTaiKH(id);
            redirectAttributes.addFlashAttribute("successMessage", "Xóa đề tài thành công!");
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Không thể xóa đề tài do lỗi ràng buộc dữ liệu: " + e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi xóa đề tài: " + e.getMessage());
        }
        return "redirect:/quanlykhoahoc/detai";
    }
}