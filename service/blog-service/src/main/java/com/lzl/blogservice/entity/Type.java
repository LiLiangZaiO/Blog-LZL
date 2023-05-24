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
 * 博客类型
 * </p>
 *
 * @author LiZeLin
 * @since 2022-11-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Type对象", description="博客类型")
public class Type implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "博客类型ID")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;


    @ApiModelProperty(value = "博客类型名称")
    private String typeName;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "博客类型创建时间")
    private Date createTime;


}
