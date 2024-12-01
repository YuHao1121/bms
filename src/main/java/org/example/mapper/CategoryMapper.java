package org.example.mapper;


import org.apache.ibatis.annotations.*;
import org.example.entity.Categories;

import java.util.List;

@Mapper
public interface CategoryMapper {


    // 查询所有分类
    @Select("SELECT * FROM categories")
    List<Categories> findAllCategories();
    //根据name查询分类
    @Select("SELECT * FROM categories where name=#{name}")
    Categories findByCategoryName(String name);

    @Select("SELECT * FROM categories where category_id=#{categoryId}")
    Categories findByCategoryId(Integer categoryId);

    @Insert("insert into categories(name) values (#{name})")
    void addCategory(String name);

    @Update("update categories set name=#{name} where category_id=#{categoryId}")
    void updateCategory(Integer categoryId, String name);


    @Delete("delete from categories where category_id=#{categoryId}")
    void deleteCategory(Integer categoryId);
}
