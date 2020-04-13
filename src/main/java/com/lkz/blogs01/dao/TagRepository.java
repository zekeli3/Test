package com.lkz.blogs01.dao;

import com.lkz.blogs01.po.Tag;
import com.lkz.blogs01.po.Type;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag,Long> {

    Tag findByName(String name);

    @Query(value = "SELECT t.id,t.name,count(tt.blogs_id) as blog_number FROM t_tag t LEFT JOIN t_blog_tags tt ON t.id = tt.tags_id LEFT JOIN t_blog b ON tt.blogs_id = b.id WHERE b.published = true GROUP BY t.id ORDER BY blog_number DESC", nativeQuery = true)
    List<Object[]> findListTop();
    @Query("select t from Tag t")
    List<Tag>findTop(Pageable pageable);


}

