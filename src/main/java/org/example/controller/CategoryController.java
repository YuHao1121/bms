package org.example.controller;

import org.example.entity.Categories;
import org.example.entity.Result;
import org.example.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    // 获取所有图书分类
    @GetMapping("/categoryInfo")
    public Result getAllCategories() {
        List<Categories> categories = categoryService.getAllCategories();
        if (categories != null && !categories.isEmpty()) {
            return Result.success(categories);
        }
        return Result.error("没有分类数据");
    }

    //新增一个图书分类
    @PostMapping("/addCategory")
    public Result addCategory(@RequestBody Categories categories) {
        Categories category = categoryService.findByCategoryName(categories.getName());
        if (category == null) {
            categoryService.addCategory(categories.getName());
            return Result.success();
        }
        return Result.error("该图书分类已存在");
    }

    //更新图书分类信息
    //这里还需要判断分类名称是否重合
    @PutMapping("/update")
    public Result updateCategory(@RequestBody Categories categories) {
        Categories category = categoryService.findByCategoryId(categories.getCategoryId());
        if (category != null) {
            categoryService.updateCategory(categories.getCategoryId(),categories.getName());
            return Result.success();
        }
        return Result.error("没有该图书分类");
    }

    //删除指定的图书分类
    @DeleteMapping("/delete")
    public Result deleteCategory(Integer categoryId) {
        Categories category = categoryService.findByCategoryId(categoryId);
        if (category != null) {
            categoryService.deleteCategory(categoryId);
            return Result.success();
        }
        return  Result.error("没有该图书分类");
    }
}
