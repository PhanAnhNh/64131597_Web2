package thiGiuaKi.ntu64131597;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import model.Post;

@Controller
public class PostController {
	private List<Post> postList = new ArrayList<>();

    // a. Hiển danh sách tất cả các post
    @GetMapping("/post/all")
    public String getAllPosts(Model model) {
        model.addAttribute("posts", postList);
        model.addAttribute("content", "Post-List");
        return "layout";
    }

    // b. Trang thêm mới
    @GetMapping("/post/new")
    public String showAddPostForm(Model model) {
    	model.addAttribute("post", new Post());
    	model.addAttribute("content", "Post-Addnew");
        return "layout";
    }

    @PostMapping("/post/new")
    public String addPost(@ModelAttribute Post post) {
        postList.add(post);
        return "redirect:/post/all";
    }

    // c. Xem post theo ID
    @GetMapping("/post/view/{id}")
    public String viewPost(@PathVariable int id, Model model) {
        Post found = postList.stream()
                .filter(p -> p.getId() == (id))
                .findFirst()
                .orElse(null);
        model.addAttribute("post", found);
        model.addAttribute("content", "Post-View");
        return "layout";
    }

    // d. Xóa post theo ID
    @GetMapping("/post/delete/{id}")
    public String deletePost(@PathVariable int id) {
        postList.removeIf(post -> post.getId() == (id));
        return "redirect:/post/all";
    }
}
