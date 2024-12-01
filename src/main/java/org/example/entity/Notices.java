package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notices {
    private Integer noticeId;     // 公告 ID
    private String name;           // 公告标题
    private String content;         // 公告内容
    private LocalDateTime createTime;        // 公告创建时间
}
