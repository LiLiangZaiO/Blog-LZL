package com.lzl.blogservice.entity.Vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class ArchivesFrontVo {

    @ApiModelProperty(value = "博客ID")
    private String id;

    @ApiModelProperty(value = "博客的标题")
    private String title;

    @ApiModelProperty(value = "博客的创建时间")
    private Date createTime;


}
