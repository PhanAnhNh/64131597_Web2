package Nhom2.DoAnWeb.NTUScientistInfor.controller;

import java.util.List;
import java.util.stream.Collectors;

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

import Nhom2.DoAnWeb.NTUScientistInfor.model.CongtrinhEntity;
import Nhom2.DoAnWeb.NTUScientistInfor.model.NhaKhoaHoc;
import Nhom2.DoAnWeb.NTUScientistInfor.service.CongTrinhService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/quanlykhoahoc")
public class CongTrinhController {
	@Autowired
	private CongTrinhService ctService;
	
	@GetMapping("/congtrinh")
	public String getAllCongTrinh(Model model) {
	    List<CongtrinhEntity> congTrinhList = ctService.getAllCongTrinh();
	    // Optionally format dates here if needed for more complex patterns
	    model.addAttribute("congTrinhList", congTrinhList);
	    return "congtrinhthuctien";
	}
	@GetMapping("/congtrinh/add")
    public String showAddForm(Model model) {
        model.addAttribute("congTrinh", new CongtrinhEntity());
        model.addAttribute("nkhList", ctService.getAllNKH());
        return "themCongTrinh";
    }
	
	@PostMapping("/congtrinh/add")
	public String addCongTrinh(@ModelAttribute("congTrinh") @Valid CongtrinhEntity congTrinh,
	                           BindingResult result,
	                           @RequestParam(value = "nkhIds", required = false) List<Integer> nkhIds,
	                           Model model) {
	    if (result.hasErrors()) {
	        model.addAttribute("nkhList", ctService.getAllNKH());
	        return "themCongTrinh";
	    }
	    ctService.saveCongTrinh(congTrinh, nkhIds);
	    return "redirect:/quanlykhoahoc/congtrinh";
	}
    
    
	@GetMapping("/congtrinh/edit/{id}")
	public String showEditForm(@PathVariable("id") String id, Model model) {
	    CongtrinhEntity congTrinh = ctService.getCongTrinhById(id).orElse(null);
	    
	    if (congTrinh != null) {
	        // Extract the IDs of associated scientists
	        List<Integer> nkhIds = congTrinh.getThamGia().stream()
	                .map(NhaKhoaHoc::getId)
	                .collect(Collectors.toList());
	        
	        model.addAttribute("congTrinh", congTrinh);
	        model.addAttribute("nkhList", ctService.getAllNKH());
	        model.addAttribute("nkhIds", nkhIds); // Add nkhIds to the model
	        return "suaCongTrinh";
	    } else {
	        return "redirect:/quanlykhoahoc/congtrinh";
	    }
	}
    @PostMapping("/congtrinh/edit/{id}")
    public String updateDeTaiKH(@PathVariable("id") String id,
                               @ModelAttribute("congTrinh") CongtrinhEntity congTrinh,
                               @RequestParam(value = "nkhIds", required = false) List<Integer> nkhIds) {
        System.out.println("Received nkhIds: " + nkhIds); // Debug log
        congTrinh.setCongTrinhID(id);
        ctService.updateCongTrinh(congTrinh, nkhIds);
        return "redirect:/quanlykhoahoc/congtrinh";
    }
    // chức năng tìm kiếm
    
    @GetMapping("/congtrinh/search")
    public String traCuuCongTrinh(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        List<CongtrinhEntity> congTrinhList = ctService.timKiemCongTrinh(keyword);
        model.addAttribute("congTrinhList",congTrinhList);
        model.addAttribute("keyword", keyword);
        return "congtrinhthuctien"; 
    }
    
 // Chức năng xóa 1 công trình
    @GetMapping("congtrinh/delete/{id}")
    public String deleteProject(@PathVariable String id, Model model) {
            ctService.deleteCongTrinh(id);
            model.addAttribute("congTrinhList", ctService.getAllProjects());
        return "redirect:/quanlykhoahoc/congtrinh";
    }
}
