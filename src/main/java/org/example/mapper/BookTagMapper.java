package org.example.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.entity.Tags;

import java.util.List;

@Mapper
public interface BookTagMapper {


    //根据book_id查询tag信息（tag_id，name）
    @Select("select tags.* "+
            "from tags "+
            "join book_tags on tags.tag_id=book_tags.tag_id "+
            "where book_tags.book_id=#{bookId}")
    List<Tags> findTagsByBookId(Integer bookId);


    //为图书添加book_id和tag_id的对应关系
    @Insert("insert into book_tags(book_id,tag_id)"+
            "values(#{bookId},#{tagId})")
    void addTagToBook(Integer bookId,Integer tagId);

    //// 查询是否存在图书和标签的关联关系
    @Select("SELECT COUNT(1) FROM book_tags WHERE book_id = #{bookId} AND tag_id = #{tagId}")
    int existsByBookIdAndTagId(Integer bookId, Integer tagId);
}
