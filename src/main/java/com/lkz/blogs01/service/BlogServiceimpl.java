package com.lkz.blogs01.service;

import com.lkz.blogs01.NotFoundException;
import com.lkz.blogs01.dao.BlogReposity;
import com.lkz.blogs01.po.Blog;
import com.lkz.blogs01.po.Type;
import com.lkz.blogs01.utils.MarkdownUtills;
import com.lkz.blogs01.utils.MyBeanUtils;
import com.lkz.blogs01.vo.BlogQuerry;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.lang.ref.SoftReference;
import java.util.*;

@Service
public class BlogServiceimpl implements BlogService {
    @Autowired
    private BlogReposity blogReposity;

    @Override
    public Blog getBlog(Long id) {
        return blogReposity.getOne(id);
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuerry blog) {
        return blogReposity.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                List<Predicate> predicates=new ArrayList<>();
                if(!"".equals(blog.getTitle())&& blog.getTitle()!=null){
                    predicates.add(cb.like(root.<String>get("title"),"%"+blog.getTitle()+"%"));
                }
                if (blog.getTypeId()!=null){
                    predicates.add(cb.equal(root.<Type>get("type").get("id"),blog.getTypeId()));
                }
                if (blog.isRecommend()){
                    predicates.add(cb.equal(root.<Boolean>get("recommend"),blog.isRecommend()));
                }
                cq.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        },pageable);
    }
    @Transactional
    @Override
    public Blog saveBlog(Blog blog) {
        if (blog.getId()==null){
            blog.setCreateTime(new Date());
            blog.setUpdateTime(new Date());
            blog.setViews(0);
        }else {
            blog.setUpdateTime(new Date());
        }

        return blogReposity.save(blog);
    }
    @Transactional
    @Override
    public Blog updateBlog(Long id, Blog blog) {
     Blog b=blogReposity.getOne(id);
     if (b==null){
         throw new NotFoundException("该博客不存在");
     }
     BeanUtils.copyProperties(b,blog, MyBeanUtils.getNullPropertyNames(blog));
     b.setUpdateTime(new Date());
     return blogReposity.save(blog);


    }
    @Transactional
    @Override
    public void deleteBlog(Long id) {
     blogReposity.deleteById(id);
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable) {
        return blogReposity.findAll(pageable);
    }

    @Override
    public Page<Blog> listBlog(String query, Pageable pageable) {
        return blogReposity.findByQuery(query,pageable);
    }

    @Override
    public List<Blog> listRecommendBlogTop(Integer size) {
        Sort sort=Sort.by(Sort.Direction.DESC,"updateTime");
        Pageable pageable= PageRequest.of(0,size,sort);
        return blogReposity.findTop(pageable);

    }

    @Override
    public Blog getAndConvert(Long id) {
        Blog blog=blogReposity.getOne(id);
        if (blog==null){
            throw new NotFoundException("该博客不存在");
        }
         Blog b=new Blog();
       BeanUtils.copyProperties(blog,b);
        String content=b.getContent();

        b.setContent(MarkdownUtills.markdownToHtml(content));
        return b;

    }

    @Override
    public Page<Blog> listBlog(Long tagId, Pageable pageable) {
        return blogReposity.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                Join join=root.join("tags");

                return cb.equal(join.get("id"),tagId);
            }
        },pageable);
    }

    @Override
    public Map<String, List<Blog>> archiveBlog() {
        List<String> years = blogReposity.findGroupYear();
        Map<String,List<Blog>> map=new HashMap<>();
        for (String year:years){
            map.put(year,blogReposity.findByYear(year));

        }
        return map;
    }

    @Override
    public Long countBlog() {
        return blogReposity.count();
    }
}

