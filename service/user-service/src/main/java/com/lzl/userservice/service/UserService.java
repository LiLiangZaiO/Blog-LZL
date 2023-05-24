package com.lzl.userservice.service;

import com.lzl.userservice.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

/**
 * <p>
 * 用户 服务类
 * </p>
 *
 * @author LiZeLin
 * @since 2022-11-16
 */
public interface UserService extends IService<User>, UserDetailsService {

    List<String> getIdsByNickName(String nickname);

    String getUserIdByUsername(String username);
}
