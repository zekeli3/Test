package com.lkz.blogs01.web;

import com.lkz.blogs01.service.BlogService;
import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller

public class archiveController {
    @Autowired
    private BlogService blogService;

    @GetMapping("/archives")
    public String archives(Model model) {

    model.addAttribute("archiveMap",blogService.archiveBlog());
    model.addAttribute("blogCount",blogService.countBlog());
    return "archives";
    }
}
