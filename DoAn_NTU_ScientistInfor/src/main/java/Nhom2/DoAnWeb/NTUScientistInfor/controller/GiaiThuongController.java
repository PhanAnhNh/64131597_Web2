package Nhom2.DoAnWeb.NTUScientistInfor.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import Nhom2.DoAnWeb.NTUScientistInfor.model.GiaithuongEntity;
import Nhom2.DoAnWeb.NTUScientistInfor.model.NhanGTEntity;
import Nhom2.DoAnWeb.NTUScientistInfor.repository.NhaKhoaHocReponsitory;
import Nhom2.DoAnWeb.NTUScientistInfor.service.GiaiThuongService;

@Controller
@RequestMapping("/quanlykhoahoc")
public class GiaiThuongController {
	
	@Autowired
	private GiaiThuongService giaiThuongService;

	@Autowired
	private NhaKhoaHocReponsitory nhaKhoaHocRepository;

	// Display award list
	@GetMapping("/giaithuong")
	public String giaiThuong(Model model, @RequestParam(value = "keyword", required = false) String keyword) {
		List<GiaithuongEntity> awards;
		if (keyword != null && !keyword.trim().isEmpty()) {
			awards = giaiThuongService.searchAwardsByName(keyword);
			awards.addAll(giaiThuongService.searchAwardsByScientist(keyword));
		} else {
			awards = giaiThuongService.getAllAwards();
		}
		model.addAttribute("awards", awards);
		return "giaithuong";
	}

	// Show add award form
	@GetMapping("/add-giai-thuong")
	public String themGiaiThuong(Model model) {
		model.addAttribute("award", new GiaithuongEntity());
		model.addAttribute("nhaKhoaHocs", nhaKhoaHocRepository.findAll());
		return "themGT";
	}

	// Save new award
	@PostMapping("/add-giai-thuong")
	public String saveGiaiThuong(@ModelAttribute GiaithuongEntity award, @RequestParam(value = "nkhIds", required = false) List<Integer> nkhIds) {
		giaiThuongService.saveAward(award);
		if (nkhIds != null) {
			for (Integer nkhId : nkhIds) {
				NhanGTEntity nhanGT = new NhanGTEntity(nkhId, award.getId());
				giaiThuongService.saveNhanGiaiThuong(nhanGT);
			}
		}
		return "redirect:/quanlykhoahoc/giaithuong";
	}

	// Show edit award form
	@GetMapping("/sua-giai-thuong/{id}")
	public String suaGiaiThuong(@PathVariable String id, Model model) {
		GiaithuongEntity award = giaiThuongService.getAwardById(id);
		if (award == null) {
			return "redirect:/quanlykhoahoc/giaithuong";
		}
		model.addAttribute("award", award);
		model.addAttribute("nhaKhoaHocs", nhaKhoaHocRepository.findAll());
		return "suaGT";
	}

	// Update award
	@PostMapping("/sua-giai-thuong")
	public String updateGiaiThuong(@ModelAttribute GiaithuongEntity award, @RequestParam(value = "nkhIds", required = false) List<Integer> nkhIds) {
		giaiThuongService.updateAwardWithScientists(award, nkhIds);
		return "redirect:/quanlykhoahoc/giaithuong";
	}

	// Delete award
	@PostMapping("/xoa-giai-thuong/{id}")
	public String xoaGiaiThuong(@PathVariable String id) {
		giaiThuongService.deleteAward(id);
		return "redirect:/quanlykhoahoc/giaithuong";
	}
}