package com.lzl.userservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lzl.userservice.entity.Role;
import com.lzl.userservice.entity.SecurityUser;
import com.lzl.userservice.entity.User;
import com.lzl.userservice.mapper.UserMapper;
import com.lzl.userservice.service.RoleService;
import com.lzl.userservice.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户 服务实现类
 * </p>
 *
 * @author LiZeLin
 * @since 2022-11-16
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private RoleService roleService;

    /**
     * SpringSecurity 根据账号获取用户信息
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = baseMapper.selectOne(new QueryWrapper<User>().eq("username", username));
        if (user==null){
            throw new UsernameNotFoundException("user not exist.");
        }
        SecurityUser securityUser = new SecurityUser();
        securityUser.setCurrentUserInfo(user);

        List<Role>  authorities = roleService.selectRoleByUserId(user.getId());
        securityUser.setRoles(authorities);
        return securityUser;
    }



    @Override
    public List<String> getIdsByNickName(String nickname) {
        return baseMapper.getIdsByNickName(nickname);
    }

    @Override
    public String getUserIdByUsername(String username) {
        return baseMapper.getUserIdByUsername(username);
    }


}
