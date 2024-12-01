package org.example.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.example.entity.Books;
import org.example.entity.PageBean;
import org.example.entity.Tags;
import org.example.mapper.BookMapper;
import org.example.mapper.BookTagMapper;
import org.example.mapper.TagMapper;
import org.example.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {


    @Autowired
    private BookMapper bookMapper; // 使用 MyBatis进行数据库操作

    @Override
    public Books findByBookName(String title) {

        Books book = bookMapper.findByBookName(title);
        return book;
    }

    @Override
    public Books findByBookId(Integer bookId) {
        Books book = bookMapper.findByBookId(bookId);
        return book;
    }

    @Override
    // 添加图书
    public void addBook(Books books) {
        // 插入图书到数据库
        bookMapper.insertBook(books);
    }


    @Override
    //根据bookId删除图书
    public void deleteByBookId(Integer bookId) {
        bookMapper.deleteBookById(bookId);
    }


    @Override
    public void updateBook(Books books) {
        // 更新数据库中的图书信息
        bookMapper.updateBook(books.getBookId(),books.getTitle(), books.getAuthor(),books.getPublishDate(), books.getCategoryId(),books.getCoverImg(), books.getDescription());
    }


    //获取一本书的所有标签，应该先在book_tags表中根据book_id查询到tag_id
    @Autowired
    private TagMapper tagMapper;
    @Autowired
    private BookTagMapper bookTagMapper;
    @Override
    public List<Tags> getTagsByBookId(Integer bookId) {
        return bookTagMapper.findTagsByBookId(bookId);
    }

    // 搜索图书
    @Override
    public PageBean<Books> searchBooks(Integer pageNum, Integer pageSize, String title, String author, String isbn) {
            // return bookMapper.searchBooks(title, author, isbn);
        //1.创建PageBean对象
        PageBean<Books> pageBean = new PageBean<>();

        //2.开启分页查询 -----pageHelper
        PageHelper.startPage(pageNum, pageSize);

        //3.调用mapper完成查询
        List<Books> b1 =  bookMapper.searchBooks(title, author, isbn);

        //强转？Page中提供了方法，可以获取PageHelper分页查询后，得到的总记录条数和当前页数据
        Page<Books> p = (Page<Books>) b1;

        //把数据填充到PageBean中
        //总记录数
        pageBean.setTotal(p.getTotal());
        //数据
        pageBean.setItems(p.getResult());


        return pageBean;
    }
}
