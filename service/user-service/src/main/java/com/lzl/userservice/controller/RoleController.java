package com.lzl.userservice.controller;


import com.lzl.common_utils.domain.R;
import com.lzl.userservice.entity.Role;
import com.lzl.userservice.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author LiZeLin
 * @since 2022-11-18
 */
@RestController
@RequestMapping("/role")
@Secured({"ROLE_ADMIN"})
public class RoleController {
    @Autowired
    private RoleService roleService;
    @GetMapping("/roles")
    public R getRoles(){
        List<Role> list = roleService.list();
        return R.ok().data("roles",list);
    }

}

