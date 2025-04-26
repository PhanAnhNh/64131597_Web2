package nhatpa.ntu64131597.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import nhatpa.ntu64131597.model.entitymodel;

@Repository
public interface intefaceJpa extends JpaRepository<entitymodel, Long>{
	

}
