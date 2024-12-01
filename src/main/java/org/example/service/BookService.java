package org.example.service;


import org.example.entity.Books;
import org.example.entity.PageBean;
import org.example.entity.Result;
import org.example.entity.Tags;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookService {

    //  根据书名查询图书
    Books findByBookName(String title);

    //根据bookId查询图书
    Books findByBookId(Integer id);

    //添加图书
    void addBook(Books books);

    //根据id删除图书
    void deleteByBookId(Integer bookId);

    //更新图书信息
    void updateBook(Books book);

    //获取一本书的所有标签
    List<Tags> getTagsByBookId(Integer bookId);



    //根据title 、author、isbn搜索图书
    PageBean<Books> searchBooks(Integer pageNum, Integer pageSize, String title, String author, String isbn);
//    List<Books> searchBooks(String title, String author, String isbn);
}
