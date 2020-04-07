package com.tts.TechTalentBlog;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BlogPostController {
	private static List<BlogPost> posts = new ArrayList<>();
	
	@Autowired
	private BlogPostRepository blogPostRepository;
	
	@GetMapping("/")
	public String index(BlogPost blogPost, Model model) {
		model.addAttribute(posts);
		return "blogpost/index";
	}
	
	@PostMapping("/")
	public String addPost(BlogPost blogPost, Model model) {
		BlogPost savedPost = blogPostRepository.save(blogPost);
		posts.add(savedPost);
		model.addAttribute("post", savedPost);
		return "blogpost/result";
	}
	
}
