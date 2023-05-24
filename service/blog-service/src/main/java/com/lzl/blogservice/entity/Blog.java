package com.lzl.blogservice.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 博客
 * </p>
 *
 * @author LiZeLin
 * @since 2022-11-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Blog对象", description="博客")
public class Blog implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "博客ID")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "博客所属用户")
    private String userId;

    @ApiModelProperty(value = "博客的标题")
    private String title;

    @ApiModelProperty(value = "博客的摘要")
    private String description;

    @ApiModelProperty(value = "博客的内容")
    private String content;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "博客的创建时间")
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "博客的修改时间")
    private Date updateTime;

    @ApiModelProperty(value = "博客所属的类型")
    private String typeId;

    @ApiModelProperty(value = "博客的首图")
    private String firstPicture;

    @ApiModelProperty(value = "浏览数量")
    private Long viewCount;

    @ApiModelProperty(value = "博客状态 0未发布  1已发布")
    private String status;

    private Long thumbsCount;


}
