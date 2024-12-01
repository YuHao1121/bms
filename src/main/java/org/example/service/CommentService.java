package org.example.service;

import org.apache.ibatis.annotations.Select;
import org.example.entity.Comments;
import org.example.entity.PageBean;
import org.example.entity.Result;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentService {

    void addComment(Comments comment);

    PageBean<Comments> listComments(Integer pageNum, Integer pageSize);

    Comments findCommentById(Integer parentId);

    void deleteComment(Integer commentId);

    // 根据父留言 ID 查询子留言
    List<Comments> getNestedComments();
}
