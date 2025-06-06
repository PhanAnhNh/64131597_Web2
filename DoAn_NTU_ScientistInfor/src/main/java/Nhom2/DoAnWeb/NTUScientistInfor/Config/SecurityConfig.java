package Nhom2.DoAnWeb.NTUScientistInfor.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import Nhom2.DoAnWeb.NTUScientistInfor.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig implements WebMvcConfigurer {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .userDetailsService(customUserDetailsService)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/quanlykhoahoc/trangchu", "/quanlykhoahoc/baibaokhoahoc", "/quanlykhoahoc/baibaokhoahoc/chitiet/**","/quanlykhoahoc/nhakhoahoc", "/quanlykhoahoc/nkh/{nkhId}/detail", "/image/**", "/css/**", "/js/**", "/baibaokhoahoc.css").permitAll()
                .requestMatchers("/quanlykhoahoc/dangky", "/quanlykhoahoc/detai","/quanlykhoahoc/detai/view/{id}", "/quanlykhoahoc/giaotrinh","/quanlykhoahoc/giaithuong", "/quanlykhoahoc/congtrinh", "/quanlykhoahoc/sohuutritue", "/quanlykhoahoc/thongke", "/quanlykhoahoc/thong-ke-nha-khoa-hoc", "/quanlykhoahoc/thong-ke-so-huu-tri-tue", "/quanlykhoahoc/thong-ke-de-tai", "/quanlykhoahoc/thong-ke-giao-trinh","/quanlykhoahoc/thong-ke-cong-trinh","/quanlykhoahoc/thong-ke-giai-thuong", "/quanlykhoahoc/dangnhap").permitAll()
                .requestMatchers("/quanlykhoahoc/baibaokhoahoc/them-baibao", "/quanlykhoahoc/baibaokhoahoc/sua/**", "/quanlykhoahoc/baibaokhoahoc/xoa/**", "/quanlykhoahoc/baibaokhoahoc/capnhat", "/quanlykhoahoc/baibaokhoahoc/chitiet/capnhat-tapchi/**").hasAnyAuthority("Admin", "NKH")
                .requestMatchers("/admin/**").hasAuthority("Admin")
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/quanlykhoahoc/dangnhap")
                .defaultSuccessUrl("/quanlykhoahoc/trangchu", true)
                .permitAll()
            )
            .logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/quanlykhoahoc/trangchu?logout")
                .permitAll()
            );
        
        return http.build();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}