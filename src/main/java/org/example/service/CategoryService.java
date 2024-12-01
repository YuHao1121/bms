package org.example.service;

import org.example.entity.Categories;

import java.util.List;

public interface CategoryService {

    // 获取所有图书分类111111
    List<Categories> getAllCategories();

    //根据name查询分类
    Categories findByCategoryName(String name);

    //根据id查询分类
    Categories findByCategoryId(Integer categoryId);
    //添加图书分类
    void addCategory(String name);

    //更新分类信息
    void updateCategory(Integer categoryId,String name);

    //删除分类信息
    void deleteCategory(Integer categoryId);
}
