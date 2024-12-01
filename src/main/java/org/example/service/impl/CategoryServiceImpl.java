package org.example.service.impl;

import org.example.entity.Categories;
import org.example.mapper.CategoryMapper;
import org.example.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    // 获取所有图书分类
    @Override
    public List<Categories> getAllCategories() {
        return categoryMapper.findAllCategories();
    }

    @Override
    public Categories findByCategoryName(String name) {
        return categoryMapper.findByCategoryName(name);
    }

    @Override
    public Categories findByCategoryId(Integer categoryId) {
        return categoryMapper.findByCategoryId(categoryId);
    }

    //增加图书分类
    @Override
    public void addCategory(String name) {
        categoryMapper.addCategory(name);
    }

    @Override
    public void updateCategory(Integer categoryId,String name) {
        categoryMapper.updateCategory(categoryId,name);
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        categoryMapper.deleteCategory(categoryId);
    }
}
