package org.example.service.impl;

import org.example.entity.Tags;
import org.example.mapper.BookTagMapper;
import org.example.mapper.TagMapper;
import org.example.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagMapper tagMapper;

    //获取所有图书标签
    @Override
    public List<Tags> getAllTags() {
        return tagMapper.findAllTags();
    }

    @Override
    public Tags findByTagName(String tagName) {
        return tagMapper.findByTagName(tagName);
    }

    @Override
    public Tags findByTagId(Integer tagId) {
        return tagMapper.findByTagId(tagId);
    }

    @Override
    public void addTag(String name) {
        tagMapper.addTag(name);
    }

    @Override
    public void updateTag(Integer tagId, String name) {
        tagMapper.updateTag(tagId, name);
    }

    @Override
    public void deleteTag(Integer tagId){
        tagMapper.deleteTag(tagId);
    }

    //为图书添加标签,旧去book_tags表拿到
    @Autowired
    private  BookTagMapper bookTagMapper;
    @Override
    public void addTagToBook(Integer bookId,Integer tagId) {
        bookTagMapper.addTagToBook(bookId,tagId);
    }

    // 检查是否已存在图书和标签的关联关系
    public boolean isTagAlreadyAddedToBook(Integer bookId, Integer tagId) {
        return bookTagMapper.existsByBookIdAndTagId(bookId, tagId) > 0;
    }
}
