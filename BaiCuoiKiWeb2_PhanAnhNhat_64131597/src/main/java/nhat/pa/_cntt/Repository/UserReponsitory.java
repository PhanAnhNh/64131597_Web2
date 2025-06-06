package nhat.pa._cntt.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import nhat.pa._cntt.Entity.UsersEntity;

public interface UserReponsitory extends JpaRepository<UsersEntity, Integer>{
	UsersEntity findByEmail(String email);
	UsersEntity findByEmailAndPasswordhash(String email, String passwordhash);
}
