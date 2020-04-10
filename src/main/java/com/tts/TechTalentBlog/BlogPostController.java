package com.tts.TechTalentBlog;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class BlogPostController {
	private static List<BlogPost> posts = new ArrayList<>();
	
	@Autowired
	private BlogPostRepository blogPostRepository;
	@CrossOrigin()
	@GetMapping("/")
	public String index(Model model) {
		
//		 What I used to fix issue: This is returning from our h2 database
		
		List<BlogPost> postsToDisplay = blogPostRepository.findAll(); 
		model.addAttribute("posts", postsToDisplay);
		
//		//	What we had: This is returning from a static variable
//		model.addAttribute("posts", posts);
		
		
		return "blogpost/index";
	}
	@CrossOrigin()
	@GetMapping("/posts/new")
	public String newPost (BlogPost blogPost) {
		return "blogpost/new";
	}
	@CrossOrigin()
	@PostMapping("/posts")
	public String addPost(BlogPost blogPost, Model model) {
		BlogPost savedPost = blogPostRepository.save(blogPost);
		posts.add(savedPost);
		model.addAttribute("post", savedPost);
		return "blogpost/result";
	}
	@CrossOrigin()
	@GetMapping("/posts/update/{id}")
	public String updateForm(BlogPost blogPost, @PathVariable Long id, Model model) {
		model.addAttribute("postIdToChange", id);
		return "blogpost/update";
	}
	@CrossOrigin()
	@PostMapping("/posts/update/{id}")
	public String updatePost(BlogPost blogPost, Model model, @PathVariable Long id) {

		BlogPost blogPostById = blogPostRepository.findBlogPostById(id);
		
		blogPostById.setTitle(blogPost.getTitle());
		blogPostById.setAuthor(blogPost.getAuthor());
		blogPostById.setBlogEntry(blogPost.getBlogEntry());
		
		blogPostRepository.save(blogPostById);
		
		
		// I also made an adjustment. Instead of loading the file I wanted to redirect instead
		return "redirect:/";
		//
	}
	@CrossOrigin()
	
	@RequestMapping(value = "/posts/{id}", method = RequestMethod.DELETE)
    public String deletePostWithId(@PathVariable Long id, BlogPost blogPost) {
        blogPostRepository.deleteById(id);
        return "redirect:/posts/new";
    }
	
}
