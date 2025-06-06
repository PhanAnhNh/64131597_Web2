package nhat.pa._cntt.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/phong-kham-vi-suc-khoe")
public class PatientsController {

	@GetMapping("/trangchu")
	public String trangchu() {
		return"trangchu";
	}
	
}
