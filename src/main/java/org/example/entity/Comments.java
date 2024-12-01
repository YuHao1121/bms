package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comments {
    private Integer commentId;   // 留言 ID
    private Integer id;      // 用户 ID
    private String content;      // 留言内容
    private Integer parentId;    // 父留言 ID
    private LocalDateTime createTime;     // 创建时间
    private String username;     // 用户名
    private List<Comments> children; // 子留言列表
}

