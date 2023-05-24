package com.lzl.userservice.mapper;

import com.lzl.userservice.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author LiZeLin
 * @since 2022-11-18
 */
public interface RoleMapper extends BaseMapper<Role> {

    List<Role> selectRoleByUserId(String userId);
}
