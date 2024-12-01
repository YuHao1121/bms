package org.example.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.example.entity.Books;
import org.example.entity.Comments;
import org.example.entity.PageBean;
import org.example.entity.Result;
import org.example.mapper.CommentMapper;
import org.example.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    //添加留言
    @Override
    public void addComment(Comments comment) {

        // 调用 Mapper 层插入留言
        commentMapper.insertComment(comment);
    }

    //分页展示留言列表
    @Override
    public PageBean<Comments> listComments(Integer pageNum, Integer pageSize) {
        //1.创建PageBean对象
        PageBean<Comments> pageBean = new PageBean<>();

        //2.开启分页查询 -----pageHelper
        PageHelper.startPage(pageNum, pageSize);

        //3.调用mapper完成查询
        List<Comments> b1 =  commentMapper.listComments();

        //强转？Page中提供了方法，可以获取PageHelper分页查询后，得到的总记录条数和当前页数据
        Page<Comments> p = (Page<Comments>) b1;

        //把数据填充到PageBean中
        //总记录数
        pageBean.setTotal(p.getTotal());
        //数据
        pageBean.setItems(p.getResult());

        return pageBean;
    }

    @Override
    public Comments findCommentById(Integer parentId) {
        return commentMapper.findCommentById(parentId);
    }

    @Override
    public void deleteComment(Integer commentId) {
        commentMapper.deleteCommentByCommentId(commentId);
    }


    @Override
    public List<Comments> getNestedComments() {
        // 查询所有顶级留言（父留言 ID 为 0）
        List<Comments> topLevelComments = commentMapper.findCommentsByParentId(0);

        // 递归查询子留言
        for (Comments comment : topLevelComments) {
            populateChildren(comment);
        }

        return topLevelComments;
    }
    private void populateChildren(Comments parentComment) {
        List<Comments> children = commentMapper.findCommentsByParentId(parentComment.getCommentId());
        if (children != null && !children.isEmpty()) {
            parentComment.setChildren(children);
            for (Comments child : children) {
                populateChildren(child);
            }
        }
    }
}
