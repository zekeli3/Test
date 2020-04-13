package com.lkz.blogs01.service;

import com.lkz.blogs01.po.Blog;
import com.lkz.blogs01.vo.BlogQuerry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface BlogService {
   Blog getBlog(Long id);
   Page<Blog> listBlog(Pageable pageable, BlogQuerry blog);
   Blog saveBlog(Blog blog);
   Blog updateBlog(Long id,Blog blog);
   void deleteBlog(Long id);
   Page<Blog>listBlog(Pageable pageable);
   Page<Blog>listBlog(String query,Pageable pageable);
   List<Blog>listRecommendBlogTop(Integer size);
   Blog getAndConvert(Long id);
   Page<Blog>listBlog(Long tagId,Pageable pageable);
   Map<String,List<Blog>> archiveBlog();
   Long countBlog();

}
