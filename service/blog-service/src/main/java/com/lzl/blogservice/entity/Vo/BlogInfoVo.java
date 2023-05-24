package com.lzl.blogservice.entity.Vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class BlogInfoVo {

    @ApiModelProperty(value = "博客ID")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "博客类型名称")
    private String typeName;

    @ApiModelProperty(value = "用户昵称")
    private String nickname;

    @ApiModelProperty(value = "博客的标题")
    private String title;

    @ApiModelProperty(value = "博客的首图")
    private String firstPicture;

    @ApiModelProperty(value = "浏览数量")
    private Long viewCount;

    @ApiModelProperty(value = "博客的修改时间")
    private Date updateTime;

    @ApiModelProperty(value = "博客状态 0未发布  1已发布")
    private String status;

}
