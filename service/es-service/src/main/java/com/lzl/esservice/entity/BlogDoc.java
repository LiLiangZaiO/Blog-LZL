package com.lzl.esservice.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class BlogDoc {

    private String id;

    private String typeName;

    private String description;

    private String avatar;

    private String nickname;

    private String title;

    private String firstPicture;

    private Long viewCount;

    private Long thumbsCount;

    private Date createTime;

    private List<String> suggestion;

    public BlogDoc(Blog blog){
        this.id = blog.getId();
        this.typeName = blog.getTypeName();
        this.description = blog.getDescription();
        this.avatar = blog.getAvatar();
        this.nickname = blog.getNickname();
        this.title = blog.getTitle();
        this.firstPicture = blog.getFirstPicture();
        this.viewCount = blog.getViewCount();
        this.thumbsCount = blog.getThumbsCount();
        this.createTime = blog.getCreateTime();
        // 自动补全字段的处理
        this.suggestion = new ArrayList<>();
        this.suggestion.add(this.title);
        this.suggestion.add(this.nickname);

    }

}
