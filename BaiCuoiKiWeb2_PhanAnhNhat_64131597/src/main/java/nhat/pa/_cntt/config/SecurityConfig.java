package nhat.pa._cntt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    	http
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/phong-kham-vi-suc-khoe/trangchu", "/phong-kham-vi-suc-khoe/dat-lich-kham", "/css/**", "/js/**", "/images/**", "/bac-si-phong-kham/dang-nhap", "/bac-si-phong-kham/dang-ky").permitAll()
            .requestMatchers("/bac-si-phong-kham/admin", "/bac-si-phong-kham/quan-ly-lich-hen", "/bac-si-phong-kham/chitiet-benhnhan", "/bac-si-phong-kham/s ua-lich-hen", "/bac-si-phong-kham/xac-nhan-kham", "/bac-si-phong-kham/chi-tiet-bac-si", "/bac-si-phong-kham/huy-xac-nhan-kham", "/bac-si-phong-kham/xoa-lich-hen", "/bac-si-phong-kham/them-bac-si").hasRole("ADMIN")
            .anyRequest().authenticated()
        )
        .formLogin(form -> form
            .loginPage("/bac-si-phong-kham/dang-nhap")
            .defaultSuccessUrl("/bac-si-phong-kham/admin", true)
            .failureUrl("/bac-si-phong-kham/dang-nhap?error=true")
            .permitAll()
        )
        .logout(logout -> logout
            .logoutUrl("/bac-si-phong-kham/dang-xuat")
            .logoutSuccessUrl("/bac-si-phong-kham/dang-nhap?logout=true")
            .invalidateHttpSession(true)
            .deleteCookies("JSESSIONID")
            .permitAll()
        );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}