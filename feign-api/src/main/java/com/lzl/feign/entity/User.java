package com.lzl.feign.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 用户
 * </p>
 *
 * @author LiZeLin
 * @since 2022-11-16
 */
@Data
public class User {

    private String id;

    private String nickname;

    private String username;

    private String password;

    private String email;

    private String avatar;

    private String status;

    private Date createTime;

    private Date updateTime;


}
