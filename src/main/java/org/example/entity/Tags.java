package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tags {
    private Integer tagId;        // 标签ID
    private String name;          // 标签名称
}

