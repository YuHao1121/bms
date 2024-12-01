package org.example.service;

import org.example.entity.Tags;

import java.util.List;

public interface TagService {
    //获取所有图书标签
    List<Tags> getAllTags();

    //根据name获取标签
    Tags findByTagName(String tagName);

    //根据id查询图书标签
    Tags findByTagId(Integer tagId);

    //添加图书分类
    void addTag(String name);

    //更新标签信息
    void updateTag(Integer tagId, String name);

    //删除标签信息
    void deleteTag(Integer tagId);

    //为图书添加标签
    void addTagToBook(Integer bookId,Integer tagId);

    //检查是否已存在图书和标签的关联关系
    boolean isTagAlreadyAddedToBook(Integer bookId, Integer tagId);
}
