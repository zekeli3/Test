package com.lkz.blogs01.service;

import com.lkz.blogs01.po.Comment;

import java.util.List;

public interface CommentService {
    List<Comment>listCommentByBlogId(Long blogId);
    Comment saveComment(Comment comment);
}
