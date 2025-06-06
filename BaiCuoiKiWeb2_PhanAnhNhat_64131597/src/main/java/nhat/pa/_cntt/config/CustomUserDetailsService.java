package nhat.pa._cntt.config;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import nhat.pa._cntt.Entity.UsersEntity;
import nhat.pa._cntt.Repository.UserReponsitory;

@Service
public class CustomUserDetailsService implements UserDetailsService{
	 @Autowired
	    private UserReponsitory userRepository;

	    @Override
	    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
	        UsersEntity user = userRepository.findByEmail(email);
	        if (user == null) {
	            throw new UsernameNotFoundException("User not found with email: " + email);
	        }
	        return new User(
	                user.getEmail(),
	                user.getPasswordhash(),
	                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole().name().toUpperCase()))
	        );
	    }
}
