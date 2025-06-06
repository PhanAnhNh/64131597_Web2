package nhat.pa._cntt.Service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import nhat.pa._cntt.Entity.UsersEntity;
import nhat.pa._cntt.Repository.UserReponsitory;

@Service
public class UsersService {
	@Autowired
    private UserReponsitory userRepository;
	
	@Autowired
    private PasswordEncoder passwordEncoder;

	public UsersEntity registerUser(UsersEntity user) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new RuntimeException("Email already exists");
        }
        user.setPasswordhash(passwordEncoder.encode(user.getPasswordhash()));
        user.setCreatedAt(LocalDateTime.now());
        return userRepository.save(user);
    }

    public UsersEntity findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    public UsersEntity authenticate(String email, String password) {
        UsersEntity user = userRepository.findByEmail(email);
        if (user != null && passwordEncoder.matches(password, user.getPasswordhash())) {
            return user;
        }
        return null;
    }
    
    public UsersEntity findById(Integer userId) {
		return userRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException("Người dùng không tồn tại"));
	}
}
