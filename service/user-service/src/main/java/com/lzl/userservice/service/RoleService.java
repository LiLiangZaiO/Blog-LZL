package com.lzl.userservice.service;

import com.lzl.userservice.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author LiZeLin
 * @since 2022-11-18
 */
public interface RoleService extends IService<Role> {

    List<Role> selectRoleByUserId(String userId);
}
