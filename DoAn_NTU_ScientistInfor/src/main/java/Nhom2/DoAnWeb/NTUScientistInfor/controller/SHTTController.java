package Nhom2.DoAnWeb.NTUScientistInfor.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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
import Nhom2.DoAnWeb.NTUScientistInfor.model.SoHuuTriTueEntity;
import Nhom2.DoAnWeb.NTUScientistInfor.service.SHTTService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/quanlykhoahoc")
public class SHTTController {

    @Autowired
    private SHTTService shttService;

    // Display SHTT list with search
    @GetMapping("/sohuutritue")
    public String trangSHTT(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        List<SoHuuTriTueEntity> shttList = shttService.searchSHTT(keyword);
        model.addAttribute("shttList", shttList);
        model.addAttribute("keyword", keyword);
        return "soHuuTriTue";
    }

    // Add SHTT form
    @GetMapping("/add-shtt")
    public String themSHTT(Model model) {
        model.addAttribute("shtt", new SoHuuTriTueEntity());
        model.addAttribute("nhaKhoaHocs", shttService.getAllNhaKhoaHoc());
        return "themSHTT";
    }

    // Save new SHTT
    @PostMapping("/add-shtt")
    public String saveSHTT(@Valid @ModelAttribute SoHuuTriTueEntity shtt, BindingResult result,
                           @RequestParam(value = "nkhIds", required = false) String nkhIds, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("nhaKhoaHocs", shttService.getAllNhaKhoaHoc());
            return "themSHTT";
        }
        List<Integer> nkhIdList = nkhIds != null && !nkhIds.isEmpty()
                ? Arrays.stream(nkhIds.split(","))
                        .map(Integer::parseInt)
                        .collect(Collectors.toList())
                : null;
        shttService.saveSHTT(shtt, nkhIdList);
        return "redirect:/quanlykhoahoc/sohuutritue";
    }

    // Edit SHTT form
    @GetMapping("/edit-shtt/{id}")
    public String editSHTTForm(@PathVariable String id, Model model) {
        Optional<SoHuuTriTueEntity> shtt = shttService.getSHTTById(id);
        if (shtt.isPresent()) {
            model.addAttribute("shtt", shtt.get());
            model.addAttribute("nhaKhoaHocs", shttService.getAllNhaKhoaHoc());
            return "suaSHTT";
        }
        return "redirect:/quanlykhoahoc/sohuutritue";
    }

    @PostMapping("/edit-shtt")
    public String updateSHTT(@Valid @ModelAttribute SoHuuTriTueEntity shtt, BindingResult result,
                            @RequestParam(value = "nkhIds", required = false) String nkhIds, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("nhaKhoaHocs", shttService.getAllNhaKhoaHoc());
            return "suaSHTT";
        }
        
        List<Integer> nkhIdList = nkhIds != null && !nkhIds.isEmpty()
                ? Arrays.stream(nkhIds.split(","))
                        .map(Integer::parseInt)
                        .collect(Collectors.toList())
                : null;
        
        shttService.updateSHTT(shtt, nkhIdList);
        return "redirect:/quanlykhoahoc/sohuutritue";
    }

    // Delete SHTT
    @PostMapping("/xoa-shtt/{id}")
    public String deleteSHTT(@PathVariable String id) {
        shttService.deleteSHTT(id);
        return "redirect:/quanlykhoahoc/sohuutritue";
    }
}