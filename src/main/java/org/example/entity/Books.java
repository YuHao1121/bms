package org.example.entity;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Books {

    private Integer bookId;       // 图书ID

    private String title;         // 书名

    private String author;        // 作者

    private String isbn;          // ISBN编号

    private String publishDate;   // 出版日期

    private Integer categoryId;   // 分类ID（外键）

    @URL
    private String coverImg;     //图书封面地址

    private String description;   // 图书简介

}
