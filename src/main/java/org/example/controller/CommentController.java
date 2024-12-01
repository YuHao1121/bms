package org.example.controller;

import org.example.entity.Comments;
import org.example.entity.PageBean;
import org.example.entity.Result;
import org.example.service.CommentService;
import org.example.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import static java.time.LocalDateTime.now;

@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    //发布留言
    @PostMapping("/add")
//    @PreAuthorize("hasAnyRole('reader', 'admin')")
    public Result addComment(@RequestBody Comments comments) {

        //验证留言内容不能为空
        if(comments.getContent() == null || comments.getContent().trim().isEmpty()){
            return Result.error("留言内容不能为空");
        }

        //获取当前用户id
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");

        comments.setId(id);
        comments.setContent(comments.getContent().trim());
        comments.setParentId(comments.getParentId() == null?0:comments.getParentId());
        comments.setCreateTime(now());


        // 调用 Service 层逻辑
        commentService.addComment(comments);
        return Result.success(comments);

    }

    //展示留言列表
    @GetMapping("/list")
//    @PreAuthorize("hasAnyRole('reader', 'admin')")
    public Result<PageBean<Comments>> list(
            @RequestParam(defaultValue = "1") Integer pageNum, //页数
            @RequestParam(defaultValue = "10") Integer pageSize //每页大小
            /*@RequestParam(required = false ) String content*/
    ){
        PageBean<Comments> pageBean = commentService.listComments(pageNum, pageSize);
        if(pageBean!=null&&pageBean.getTotal() > 0){
            return Result.success(pageBean);
        }
        return Result.error("没有留言");
    }

    //回复留言
    @PostMapping("/reply")
//    @PreAuthorize("hasAnyRole('reader', 'admin')")
    public Result replyComment(@RequestBody Map<String, Object> requestData) {
        Integer parentId = (Integer) requestData.get("parentId");
        String content = (String) requestData.get("content");
        // 验证回复内容是否为空
        if (content == null || content.trim().isEmpty()) {
            return Result.error("回复内容不能为空");
        }

        // 验证父留言是否存在
        Comments parentComment = commentService.findCommentById(parentId);
        if (parentComment == null) {
            return Result.error("父留言不存在");
        }

        // 获取当前登录用户 id
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        if (id == null) {
            return Result.error("用户未登录");
        }

        // 构建新的回复留言
        Comments replyComment = new Comments();
        replyComment.setId(id);
        replyComment.setContent(content.trim());
        replyComment.setParentId(parentId); // 设置父留言 ID
        replyComment.setCreateTime(now());
        // 插入回复留言
        commentService.addComment(replyComment);

        // 查询完整留言信息，包括 username
        Comments completeComment = commentService.findCommentById(replyComment.getCommentId());
        return Result.success(completeComment);
    }

    //删除留言
    @DeleteMapping("/delete/{commentId}")
//    @PreAuthorize("hasAnyRole('reader', 'admin')")
    public Result deleteComment(@PathVariable Integer commentId) {

        // 验证留言是否存在
        Comments comment = commentService.findCommentById(commentId);
        if (comment == null) {
            return Result.error("留言不存在");
        }

        // 验证当前用户是否有权限删除（管理员或留言所属用户）
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer currentUserId = (Integer) map.get("id");
//        String currentUserRole = (String) map.get("role");
//        boolean isAdmin = "admin".equalsIgnoreCase(currentUserRole);
        boolean isOwner = currentUserId.equals(comment.getId());
        if (/*!isAdmin &&*/ !isOwner) {
            return Result.error("无权限删除该留言");
        }

        commentService.deleteComment(commentId);
        return Result.success();
    }

    //获取嵌套留言列表
    @GetMapping("/nested-list")
//    @PreAuthorize("hasAnyRole('reader', 'admin')")
    public Result listNestedComments(){
        // 调用 Service 层逻辑获取嵌套留言
        List<Comments> nestedComments = commentService.getNestedComments();
        if (nestedComments != null && !nestedComments.isEmpty()) {
            return Result.success(nestedComments);
        }
        return Result.error("没有留言");
    }
}
