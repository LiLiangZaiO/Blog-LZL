package com.lzl.userservice.service.impl;

import com.lzl.userservice.entity.Role;
import com.lzl.userservice.mapper.RoleMapper;
import com.lzl.userservice.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author LiZeLin
 * @since 2022-11-18
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    /**
     * 根据用户id获取用户角色
     * @param userId
     * @return
     */
    @Override
    public List<Role> selectRoleByUserId(String userId) {

        List<Role> roles = baseMapper.selectRoleByUserId(userId);
        return roles;
    }
}
