package com.lzl.photoservice.entity;

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
 * 照片墙
 * </p>
 *
 * @author LiZeLin
 * @since 2022-11-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Picture对象", description="照片墙")
public class Picture implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "照片ID")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "照片的标题")
    private String title;

    @ApiModelProperty(value = "照片的摘要")
    private String description;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "照片的创建时间")
    private Date createTime;

    @ApiModelProperty(value = "照片的地址")
    private String address;

    @ApiModelProperty(value = "浏览数量")
    private Long viewCount;

    @ApiModelProperty(value = "照片状态 0未发布  1已发布")
    private String status;


}
