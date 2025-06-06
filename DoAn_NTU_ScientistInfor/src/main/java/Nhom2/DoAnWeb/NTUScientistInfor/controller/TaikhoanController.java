package Nhom2.DoAnWeb.NTUScientistInfor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import Nhom2.DoAnWeb.NTUScientistInfor.model.TaiKhoanEntity;
import Nhom2.DoAnWeb.NTUScientistInfor.model.TaiKhoanEntity.LoaiTaiKhoan;
import Nhom2.DoAnWeb.NTUScientistInfor.repository.TaikhoanReponsitory;
@Controller
@RequestMapping("/quanlykhoahoc")
public class TaikhoanController {
	@Autowired
    private TaikhoanReponsitory taiKhoanRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    
    @GetMapping("/dangnhap")
    public String getDangNhap() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !auth.getPrincipal().equals("anonymousUser")) {
            return "redirect:/quanlykhoahoc/trangchu";
        }
        return "dangnhap";
    }
    
    @GetMapping("/dangky")
    public String getDangKy(Model model) {
        model.addAttribute("taiKhoan", new TaiKhoanEntity());
        return "dangki";
    }
    
    @PostMapping("/dangky")
    public String postDangKy(@ModelAttribute TaiKhoanEntity taiKhoan, Model model) {
        // Check if username already exists
        if (taiKhoanRepository.existsById(taiKhoan.getTenTaiKhoan())) {
            model.addAttribute("error", "Tên đăng nhập đã tồn tại");
            return "dangki";
        }
        
        // Encrypt password
        taiKhoan.setMatKhau(passwordEncoder.encode(taiKhoan.getMatKhau()));
        
        // Save to database
        taiKhoanRepository.save(taiKhoan);
        
        return "redirect:/quanlykhoahoc/dangnhap?registered";
    }
    
    @ModelAttribute("currentUser")
    public String getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !auth.getPrincipal().equals("anonymousUser")) {
            System.out.println("Current user: " + auth.getName() + ", Principal: " + auth.getPrincipal());
            return auth.getName();
        }
        System.out.println("No authenticated user, Principal: " + (auth != null ? auth.getPrincipal() : "null"));
        return null;
    }
    
    @ModelAttribute("isAuthenticated")
    public boolean isAuthenticated() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isAuthenticated = auth != null && auth.isAuthenticated() && !auth.getPrincipal().equals("anonymousUser");
        System.out.println("isAuthenticated: " + isAuthenticated + ", Principal: " + (auth != null ? auth.getPrincipal() : "null"));
        return isAuthenticated;
    }
}
