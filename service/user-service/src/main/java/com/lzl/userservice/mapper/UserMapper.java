package com.lzl.userservice.mapper;

import com.lzl.userservice.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 用户 Mapper 接口
 * </p>
 *
 * @author LiZeLin
 * @since 2022-11-16
 */
public interface UserMapper extends BaseMapper<User> {

    List<String> getIdsByNickName(String nickname);

    String getUserIdByUsername(String username);
}
