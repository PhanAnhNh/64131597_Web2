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
public class HomeController {
	private final List<Page> pages = new ArrayList<>();
	@GetMapping("/")
    public String dasdboard(Model model) {
		model.addAttribute("content", "Dasdboard");
        return "layout"; // Trả về file Dasdboard.html
    }
	
	@GetMapping("/page/all")
    public String list(Model model) {
		model.addAttribute("pages", pages);
		model.addAttribute("content", "Page-List");
        return "layout"; // Trả về file Dasdboard.html
    }
	
	@GetMapping("/page/new")
	public String addnew(Model model) {
	    model.addAttribute("content", "Page-Addnew"); // tạo file pageAdd-content.html tương tự
	    return "layout";
	}
	
	@PostMapping("/page/new")
    public String addPage(
            @RequestParam String pageName,
            @RequestParam String keyword,
            @RequestParam String content,
            @RequestParam int parentPageId) {

        pages.add(new Page(pageName, keyword, content, parentPageId));
        return "redirect:/page/all"; // Sau khi thêm thì chuyển về danh sách
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
        model.addAttribute("content", "Page-View");
        return "layout";
    }
}
