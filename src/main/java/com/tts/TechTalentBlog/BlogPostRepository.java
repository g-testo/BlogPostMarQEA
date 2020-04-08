package com.tts.TechTalentBlog;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogPostRepository extends JpaRepository<BlogPost, Long>{
	BlogPost findBlogPostById(Long id);
}
