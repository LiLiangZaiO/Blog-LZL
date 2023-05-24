package com.lzl.ossservice.entity;

import lombok.Data;
import java.util.Date;


@Data
public class Picture{

    private String id;

    private String title;

    private String description;

    private Date createTime;

    private String address;

    private Long viewCount;

    private String status;


}
