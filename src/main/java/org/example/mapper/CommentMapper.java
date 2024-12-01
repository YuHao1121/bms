package org.example.mapper;

import org.apache.ibatis.annotations.*;
import org.example.entity.Comments;

import java.util.List;

@Mapper
public interface CommentMapper {

    //插入留言
    @Insert("INSERT INTO comments (id, content, parent_id, create_time) " +
            "VALUES (#{id}, #{content}, #{parentId}, #{createTime})")
    @Options(useGeneratedKeys = true, keyProperty = "commentId")
    void insertComment(Comments comment);

    //查询所有留言
    @Select("SELECT c.comment_id, c.id, c.content, c.parent_id, c.create_time, u.username " +
            "FROM comments c " +
            "LEFT JOIN users u ON c.id = u.id " +
            "ORDER BY c.create_time DESC")
    List<Comments> listComments();

    //根据parentId查询留言
    @Select("SELECT c.comment_id, c.id, c.content, c.parent_id, c.create_time, u.username " +
            "FROM comments c " +
            "LEFT JOIN users u ON c.id = u.id " +
            "WHERE comment_id = #{commentId} " )
    Comments findCommentById(Integer commentId);

    //根据commentId查找留言
    @Delete("DELETE FROM comments WHERE comment_id = #{commentId}")
    void deleteCommentByCommentId(Integer commentId);

    // 根据父留言 ID 查询子留言
    @Select("SELECT c.comment_id, c.id, u.username, c.content, c.parent_id, c.create_time " +
            "FROM comments c " +
            "LEFT JOIN users u ON c.id = u.id " +
            "WHERE c.parent_id = #{parentId} " +
            "ORDER BY c.create_time ASC")
    List<Comments> findCommentsByParentId(Integer parentId);
}
