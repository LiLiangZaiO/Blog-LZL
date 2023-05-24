package com.lzl.esservice.entity;

import lombok.Data;

@Data
public class RequestParams {

    private String key;
    private Integer page;
    private Integer size;
    private String typeName;
    private String sortBy;

}
