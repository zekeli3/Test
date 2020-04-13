package com.lkz.blogs01.dao;

import com.lkz.blogs01.po.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentReposity extends JpaRepository<Comment,Long> {
    List<Comment> findByBlogId(Long blogId, Sort sort);
}
