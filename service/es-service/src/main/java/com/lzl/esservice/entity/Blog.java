package com.lzl.esservice.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 博客
 * </p>
 */
@Data
public class Blog {

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
