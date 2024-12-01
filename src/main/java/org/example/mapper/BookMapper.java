package org.example.mapper;

import org.apache.ibatis.annotations.*;
import org.example.entity.Books;

import java.util.List;

@Mapper
public interface BookMapper {

//    //查询所有图书
//    Books findBook();
//
//    //根据isbn查询所有图书
//    Books findBookByIsbn(String isbn);

    //根据id查询所有图书
    @Select("select * from books where book_id=#{bookID}")
    Books findByBookId(Integer bookID);

    //根据图书名查询图书
    @Select("select * from books where title=#{title}")
    Books findByBookName(String title);

    // 插入图书
    @Insert("insert into books(title,author,isbn,publish_date,category_id,cover_img,description)"+
            "values (#{title},#{author},#{isbn},#{publishDate},#{categoryId},#{coverImg},#{description})")
    void insertBook(Books book);

    //根据图书id删除图书
    @Delete("delete from books where book_id=#{bookId}")
    void deleteBookById(Integer bookId);

    //更新图书信息
    @Update("update books set title=#{title},author=#{author},publish_date=#{publishDate},category_id=#{categoryId},cover_img=#{coverImg},description=#{description} where book_id=#{bookId}")
    void updateBook(Integer bookId,String title, String author, String publishDate, Integer categoryId, String coverImg,String description);

    //搜索图书
    //select * from books limit 3,2;
    //起始索引=（页码-1）*每页条数
    //count（*）获取总记录数   select count（*） from books

    //使用 <script> 和 <if> 标签，根据传入的参数动态拼接查询条件。
    /*
    如果 title 参数不为空，则在 SQL 中加入 title LIKE '%title%' 条件，进行模糊查询。
    如果 author 参数不为空，则在 SQL 中加入 author LIKE '%author%' 条件，进行模糊查询。
    如果 isbn 参数不为空，则在 SQL 中加入 isbn = isbn 条件，进行精确查询。*/
    @Select("<script>" +
            "SELECT * FROM books " +
            "WHERE 1=1 " +
            "<if test='title != null and title.trim() != \"\"'> " +
            "AND title LIKE CONCAT('%', #{title}, '%') " +
            "</if>" +
            "<if test='author != null and author.trim() != \"\"'> " +
            "AND author LIKE CONCAT('%', #{author}, '%') " +
            "</if>" +
            "<if test='isbn != null and isbn.trim() != \"\"'> " +
            "AND isbn = #{isbn} " +
            "</if>" +
            "</script>")
    List<Books> searchBooks(@Param("title") String title,
                            @Param("author") String author,
                            @Param("isbn") String isbn);

}
