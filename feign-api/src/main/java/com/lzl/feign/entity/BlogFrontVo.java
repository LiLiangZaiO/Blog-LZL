package com.lzl.feign.entity;

import lombok.Data;

import java.util.Date;

@Data
public class BlogFrontVo {
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


}
