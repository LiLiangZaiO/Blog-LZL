package com.lzl.blogservice.entity.Vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class BlogFrontVo {

    @ApiModelProperty(value = "博客ID")
    private String id;

    @ApiModelProperty(value = "博客类型名称")
    private String typeName;

    @ApiModelProperty(value = "博客的摘要")
    private String description;

    @ApiModelProperty(value = "用户头像")
    private String avatar;

    @ApiModelProperty(value = "用户昵称")
    private String nickname;

    @ApiModelProperty(value = "博客的标题")
    private String title;

    @ApiModelProperty(value = "博客的首图")
    private String firstPicture;

    @ApiModelProperty(value = "浏览数量")
    private Long viewCount;

    @ApiModelProperty(value = "点赞数量")
    private Long thumbsCount;

    @ApiModelProperty(value = "博客的创建时间")
    private Date createTime;


}
