package nhatpa.ntu64131597.controller;

import org.springframework.web.bind.annotation.RequestMapping;

import nhatpa.ntu64131597.model.entitymodel;
import nhatpa.ntu64131597.repository.intefaceJpa;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers")
public class CustomerRestController {
	
	@Autowired
	private intefaceJpa customerRepository;
	
	@GetMapping
    public List<entitymodel> getAllCustomers() {
        return customerRepository.findAll();
    }

    @PostMapping
    public entitymodel createCustomer(@RequestBody entitymodel customer) {
        return customerRepository.save(customer);
    }
}
