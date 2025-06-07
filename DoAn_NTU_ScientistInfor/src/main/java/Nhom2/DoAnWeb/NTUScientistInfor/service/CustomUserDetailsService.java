package Nhom2.DoAnWeb.NTUScientistInfor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import Nhom2.DoAnWeb.NTUScientistInfor.model.TaiKhoanEntity;
import Nhom2.DoAnWeb.NTUScientistInfor.repository.TaikhoanReponsitory;

@Service
public class CustomUserDetailsService implements UserDetailsService{
	@Autowired
    private TaikhoanReponsitory taikhoanRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Find user by username (TENTAIKHOAN)
        TaiKhoanEntity taiKhoan = taikhoanRepository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        // Map LOAITAIKHOAN to Spring Security authorities
        String authority = taiKhoan.getLoaiTaiKhoan().name();

        // Create UserDetails object
        return User.builder()
                .username(taiKhoan.getTenTaiKhoan())
                .password(taiKhoan.getMatKhau())
                .authorities(authority) // Assign the role (e.g., "Admin" or "NKH")
                .build();
    }
}
