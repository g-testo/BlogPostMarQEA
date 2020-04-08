package com.tts.TechTalentBlog;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@Controller
public class BlogPostController {
	private static List<BlogPost> posts = new ArrayList<>();
	
	@Autowired
	private BlogPostRepository blogPostRepository;
	
	@GetMapping("/")
	public String index(BlogPost blogPost, Model model) {
		
		// What I used to fix issue: This is returning from our h2 database
		
		List<BlogPost> postsToDisplay = blogPostRepository.findAll(); 
		model.addAttribute("posts", postsToDisplay);
		
		//
		
		// What we had: This is returning from a static variable
		// model.addAttribute("posts", posts);
		//
		
		return "blogpost/index";
	}
	
	@GetMapping("/posts/new")
	public String newPost (BlogPost blogPost) {
		return "blogpost/new";
	}
	
	@PostMapping("/posts")
	public String addPost(BlogPost blogPost, Model model) {
		BlogPost savedPost = blogPostRepository.save(blogPost);
		posts.add(savedPost);
		model.addAttribute("post", savedPost);
		return "blogpost/result";
	}
	
	@GetMapping("/posts/update/{id}")
	public String updateForm(BlogPost blogPost, @PathVariable Long id, Model model) {
		model.addAttribute("postIdToChange", id);
		return "blogpost/update";
	}
	
	@PostMapping("/posts/update/{id}")
	public String updatePost(BlogPost blogPost, Model model, @PathVariable Long id) {

		BlogPost blogPostById = blogPostRepository.findBlogPostById(id);
		
		blogPostById.setTitle(blogPost.getTitle());
		blogPostById.setAuthor(blogPost.getAuthor());
		blogPostById.setBlogEntry(blogPost.getBlogEntry());
		
		blogPostRepository.save(blogPostById);
		// I also made an adjustment. Instead of loading the file I wanted to redirect instread
		return "redirect:/";
		//
	}
	
}
