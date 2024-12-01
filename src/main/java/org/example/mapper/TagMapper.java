package org.example.mapper;

import org.apache.ibatis.annotations.*;
import org.example.entity.Categories;
import org.example.entity.Tags;

import java.util.List;

@Mapper
public interface TagMapper {
    // 查询所有标签
    @Select("SELECT * FROM tags")
    List<Tags> findAllTags();

    //根据name查询标签
    @Select("SELECT * FROM tags where name=#{name}")
    Tags findByTagName(String name);

    //根据id查询标签
    @Select("SELECT * FROM tags where tag_id=#{tagId}")
    Tags findByTagId(Integer tagId);

    //添加标签
    @Insert("insert into tags(name) values (#{name})")
    void addTag(String name);

    //修改标签
    @Update("update tags set name=#{name} where tag_id=#{tagId}")
    void updateTag(Integer tagId, String name);

    //删除标签
    @Delete("delete from tags where tag_id=#{tagId}")
    void deleteTag(Integer tagId);
}
