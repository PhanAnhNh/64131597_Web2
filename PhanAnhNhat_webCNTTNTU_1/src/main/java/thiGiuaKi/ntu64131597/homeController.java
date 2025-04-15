package thiGiuaKi.ntu64131597;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import model.Page;



@Controller
public class homeController {
	private final List<Page> pages = new ArrayList<>();
	@GetMapping("/")
    public String home() {
        return "Dasdboard"; // Trả về file Dasdboard.html
    }
	@GetMapping("/page/new")
    public String addnew() {
        return "pageAdd"; // Trả về file Dasdboard.html
    }
	
	@GetMapping("/page/all")
    public String list(Model model) {
		model.addAttribute("pages", pages);
        return "pageList"; // Trả về file Dasdboard.html
    }
	
	@PostMapping("/page/new")
    public String addStudent(
            @RequestParam String pageName,
            @RequestParam String keyword,
            @RequestParam String content,
            @RequestParam int parentPageId) {
        
        pages.add(new Page(pageName, keyword, content, parentPageId));
        return "redirect:/page/new"; // Sau khi thêm
    }
	
	@GetMapping("/page/delete/id")
	public String deletePage(@RequestParam int id) {
	    pages.removeIf(page -> page.getId() == id);
	    return "redirect:/page/all";
	}
	
	@GetMapping("/page/view/id")
	public String viewPageDetail(@RequestParam int id, Model model) {
	    Page page = pages.stream()
	                     .filter(p -> p.getId() == id)
	                     .findFirst()
	                     .orElse(null);

	    model.addAttribute("page", page);
	    return "page-View"; // trang hiển thị chi tiết
	}


}
