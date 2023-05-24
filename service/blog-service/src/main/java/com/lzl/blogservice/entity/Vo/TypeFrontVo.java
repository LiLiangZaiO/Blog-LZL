package com.lzl.blogservice.entity.Vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class TypeFrontVo {

    @ApiModelProperty(value = "博客类型ID")
    private String id;

    @ApiModelProperty(value = "博客类型名称")
    private String typeName;

    @ApiModelProperty(value = "该类型创建博客的数量")
    private Long typeCount;

}
