package com.lkz.blogs01.service;

import com.lkz.blogs01.bo.TypeOrTagBlog;
import com.lkz.blogs01.po.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.github.pagehelper.PageInfo;
import java.util.List;

public interface TagService {
    Tag saveTag(Tag type);

    Tag getTag(Long id);

    Tag getTagByName(String name);

    Page<Tag> listTag(Pageable pageable);

    List<Tag> listTag();

    List<TypeOrTagBlog> listTagBlog();

    List<Tag> listTag(String ids);

    Tag updateTag(Long id, Tag type);

    void deleteTag(Long id);
    List<Tag> listTagTop(Integer size);

}
