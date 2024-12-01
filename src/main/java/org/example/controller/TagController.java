package org.example.controller;


import org.example.entity.Categories;
import org.example.entity.Result;
import org.example.entity.Tags;
import org.example.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagController {

    @Autowired
    private TagService tagService;

    //获取所有图书标签
    @GetMapping("/tagInfo")
    public Result getAllTags() {
        List<Tags> tags = tagService.getAllTags();
        if (tags != null && !tags.isEmpty()) {
            return Result.success(tags);
        }
        return Result.error("没有图书标签");
    }

    //新增一个图书标签
    @PostMapping("/addTag")
    public Result addTag(@RequestBody Tags tags) {
        Tags tag = tagService.findByTagName(tags.getName());
        if (tag == null) {
            tagService.addTag(tags.getName());
            return Result.success();
        }
        return Result.error("该图书标签已存在");
    }

    //更新图书标签信息
    @PutMapping("/update")
    public Result uptateTag(@RequestBody Tags tags) {
        Tags tag = tagService.findByTagId(tags.getTagId());
        if (tag != null) {
            tagService.updateTag(tags.getTagId(), tags.getName());
            return Result.success();
        }
        return Result.error("没有该图书标签");
    }

    //删除指定的图书标签
        @DeleteMapping("/delete")
    public Result deleteTag(Integer tagId) {
        Tags tag = tagService.findByTagId(tagId);
        if (tag != null) {
            tagService.deleteTag(tagId);
            return Result.success();
        }
        return Result.error("没有该图书标签");
    }
}
