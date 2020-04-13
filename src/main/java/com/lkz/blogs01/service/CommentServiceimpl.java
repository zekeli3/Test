package com.lkz.blogs01.service;

import com.lkz.blogs01.dao.CommentReposity;
import com.lkz.blogs01.po.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
@Service
public class CommentServiceimpl implements CommentService{
    @Autowired
    private CommentReposity commentReposity;
    @Override
    public List<Comment> listCommentByBlogId(Long blogId) {
        Sort sort= Sort.by(Sort.Direction.DESC,"createTime");
        return commentReposity.findByBlogId(blogId,sort);

    }
@Transactional
    @Override
    public Comment saveComment(Comment comment) {
        Long parentCommentId=comment.getBlog().getId();
        if (parentCommentId!=-1){
            comment.setParentComment(commentReposity.getOne(parentCommentId));
        }else {
            comment.setParentComment(null);
        }
        comment.setCreateTime(new Date());
        return commentReposity.save(comment);
    }
}
