package org.example.controller;


import org.example.entity.Books;
import org.example.entity.PageBean;
import org.example.entity.Result;
import org.example.entity.Tags;
import org.example.service.BookService;
import org.example.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
    @Autowired
    private BookService bookService;

    // 添加图书
    @PostMapping("/insertBook")
    public Result addBook(@RequestBody @Validated Books booksRequest) {

        //查询图书
        Books books = bookService.findByBookName(booksRequest.getTitle());
        if (books == null) {
            bookService.addBook(booksRequest);
            return Result.success(booksRequest);
        }
        return Result.error("该书已存在");
    }

    //删除图书
    @DeleteMapping("/delete")
    public Result deleteBook( Integer bookId) {

        //查询图书是否存在
        Books books = bookService.findByBookId(bookId);

        if(books != null) {
            bookService.deleteByBookId(bookId);
            return Result.success();
        }
        return Result.success();
    }

    //根据图书ID查看图书的详细信息。
    @GetMapping("/bookInfo/{book_id}")
    public Result getBooks(@PathVariable("book_id") Integer bookId) {
        Books book = bookService.findByBookId(bookId);
        if(book != null) {
            return Result.success(book);
        }
        return Result.error("没有该图书！");
    }

    // 修改图书信息
    @PutMapping("/update")
    public Result updateBook(/*@PathVariable("book_id") Integer bookId,
                             @RequestParam(required = false) String title,
                             @RequestParam(required = false) String author,
                             @RequestParam(required = false) String publishDate,
                             @RequestParam(required = false) Integer categoryId,
                             @RequestParam(required = false) String description*/@RequestBody @Validated Books book1) {

        Books book = bookService.findByBookId(book1.getBookId());
        if(book != null) {
            bookService.updateBook(book1);
            return Result.success();
        }
        return Result.error("图书不存在");
    }



    //根据图书名和标签名为图书添加标签
    @Autowired
    private TagService tagService;

    @PostMapping("/addTagToBook")
    public Result addTagToBook(@RequestParam String bookTitle, @RequestParam String tagName) {
        //根据图书名查找图书id
        Integer bookId = bookService.findByBookName(bookTitle).getBookId();
        if(bookId == null) {
            return Result.error("该图书不存在");
        }
        //根据标签名查找tag_id
        Integer tagId = tagService.findByTagName(tagName).getTagId();
        if(tagId == null) {
            return Result.error("该标签不存在");
        }

        //都找到，则添加进book_tags表
        // 检查是否已经存在该图书和标签的关联关系
        if (!tagService.isTagAlreadyAddedToBook(bookId, tagId)) {
            tagService.addTagToBook(bookId, tagId);
            return Result.success();
        }
        return Result.error("该图书已有该标签");
    }

    //根据图书名获取图书的所有标签
    @GetMapping("/{bookTitle}/tags")
    public Result getTagsByBookId(@PathVariable String bookTitle) {
        Integer bookId = bookService.findByBookName(bookTitle).getBookId();
        List<Tags> tags = bookService.getTagsByBookId(bookId);
        if(tags != null && !tags.isEmpty()) {
            return Result.success(tags);
        }
        return Result.error("没有标签数据");
    }

    // 基本搜索接口：按标题、作者或 ISBN 搜索图书
    @GetMapping("/search")
    public Result<PageBean<Books>> searchBooks(
            Integer pageNum, //页数
            Integer pageSize, //每页大小
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String isbn) {

        PageBean<Books> pbBooks = bookService.searchBooks(pageNum,pageSize,title, author, isbn);
        if (pbBooks != null ) {
            return Result.success(pbBooks);
        }
        return Result.error("查询失败或无数据");
    }

    //按图书分类搜索图书
    //？

    //按图书标签搜索图书
    //？
}
