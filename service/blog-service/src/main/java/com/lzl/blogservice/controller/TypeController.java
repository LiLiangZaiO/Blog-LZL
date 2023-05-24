package com.lzl.blogservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzl.blogservice.entity.Type;
import com.lzl.blogservice.service.TypeService;
import com.lzl.common_utils.domain.R;
import com.lzl.servicebase.exceptionHandler.LZLException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 博客类型 前端控制器
 * </p>
 *
 * @author LiZeLin
 * @since 2022-11-16
 */
@Api(tags = "博客类型管理")
@RestController
@RequestMapping("/type")
@Secured({"ROLE_ADMIN","ROLE_BLOG"})
public class TypeController {

    @Autowired
    private TypeService typeService;

    @ApiOperation(value = "查询所有类型")
    @GetMapping("/types")
    public R findAllType(){
        List<Type> typeList = typeService.list();
        return R.ok().data("typeList",typeList);
    }

    @PostMapping("pageTypeCondition/{current}/{limit}")
    public R pageTypeCondition(@PathVariable long current,
                               @PathVariable long limit,
                               @RequestBody(required = false) Type type){

        Page<Type> page = new Page<>(current,limit);

        QueryWrapper<Type> queryWrapper = new QueryWrapper<>();
        if (StringUtils.hasText(type.getTypeName())){
            queryWrapper.like("type_name",type.getTypeName());
        }
        queryWrapper.orderByDesc("create_time");
        IPage<Type> typePage = typeService.page(page, queryWrapper);
        return R.ok().data("typePage",typePage);
    }



    @ApiOperation("根据id删除类型")
    @DeleteMapping("/deleteType/{typeId}")
    public R  deleteType(@PathVariable String typeId){
        typeService.removeById(typeId);
        return R.ok();
    }

    @ApiOperation("添加类型")
    @PostMapping("/addType")
    public R addType(@Validated @RequestBody Type type){
        int count = typeService.count(new QueryWrapper<Type>().eq("type_name", type.getTypeName()));
        if (count>0){
            throw new LZLException(20001,"该博客类型已存在");
        }
        typeService.save(type);
        return R.ok();
    }


    @ApiOperation("修改类型")
    @PostMapping("/updateTypeInfo")
    public R updateTypeInfo(@Validated @RequestBody Type type){

        typeService.updateById(type);
        return R.ok();
    }

    @ApiOperation("根据类型Id查询类型数据")
    @GetMapping("/getTypeById/{typeId}")
    public R getTypeById(@PathVariable String typeId){

        Type type = typeService.getById(typeId);
        if (type==null){
            throw  new LZLException(20001,"该类型不存在");
        }
        return R.ok().data("type",type);

    }



}

