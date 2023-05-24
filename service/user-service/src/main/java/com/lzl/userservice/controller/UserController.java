package com.lzl.userservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lzl.common_utils.domain.R;
import com.lzl.userservice.entity.User;
import com.lzl.userservice.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 用户 前端控制器
 * </p>
 *
 * @author LiZeLin
 * @since 2022-11-16
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     *  远程调用
     */
    @GetMapping("getUserIdByUsername/{username}")
    public String getUserIdByUsername(@PathVariable("username") String username){
        String userId = userService.getUserIdByUsername(username);
        return userId;
    }



    /**
     * 远程调用
     * 根据用户id查询用户
     */

    @GetMapping("/getUserById/{id}")
    public User queryById(@PathVariable("id") String id){
        User user = userService.getById(id);
        return user;
    }

    /**
     *远程调用
     * @param nickname
     * @return
     */
    @GetMapping("/getIdsByNickName/{nickname}")
    public List<String> getIdsByNickName(@PathVariable("nickname") String nickname){
        return userService.getIdsByNickName(nickname);
    }


    @ApiOperation(value = "查询所有用户")
    @GetMapping("/users")
    public R findAllUser(){
        List<User> userList = userService.list();
        return R.ok().data("userList",userList);
    }

    /**
     * 根据token获取用户信息
     * @return
     */
    @GetMapping("/getUserInfo")
    
    public R getUserInfo(){
        ///获取当前登录用户用户名
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getOne(new QueryWrapper<User>().eq("username", username));
        return R.ok().data("name",user.getNickname()).data("avatar",user.getAvatar());
    }

//    @ApiOperation(value = "查询用户信息")
//    @GetMapping("/getUserInfo")
//    public R getUserInfo(HttpServletRequest request){
////        String userId = JwtUtils.getMemberIdByJwtToken(request);
////        User user = userService.getById(userId);
////
////        return R.ok().data("name",user.getNickname()).data("avatar",user.getAvatar());
//
//        return R.ok().data("roles","[admin]").data("name","admin")
//                .data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
//    }

    @ApiOperation(value = "注销")
    @PostMapping("/logout")
    public R logout(HttpServletRequest request){
        String jwtToken = request.getHeader("Authorization");
//        Boolean result = userService.logout(jwtToken);
        return R.ok().message("注销成功！");
    }

}

