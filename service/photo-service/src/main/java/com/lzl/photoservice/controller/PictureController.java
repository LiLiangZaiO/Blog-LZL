package com.lzl.photoservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzl.common_utils.domain.R;
import com.lzl.photoservice.entity.Picture;
import com.lzl.photoservice.service.PictureService;
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
 * 照片墙 前端控制器
 * </p>
 *
 * @author LiZeLin
 * @since 2022-11-17
 */
@Api(tags = "照片墙")
@RestController
@RequestMapping("/photo")
@Secured({"ROLE_ADMIN","ROLE_PHOTO"})
public class PictureController {
    @Autowired
    private PictureService pictureService;

    @ApiOperation(value = "查询所有照片")
    @GetMapping("/pictures")
    public R findAllPictures(){
        List<Picture> pictureList = pictureService.list();
        return R.ok().data("pictureList",pictureList);
    }

    @PostMapping("pagePictureCondition/{current}/{limit}")
    public R pagePictureCondition(@PathVariable long current,
                                  @PathVariable long limit,
                                  @RequestBody(required = false) Picture picture){

        Page<Picture> page = new Page<>(current, limit);

        QueryWrapper<Picture> queryWrapper = new QueryWrapper<>();

        if (StringUtils.hasText(picture.getStatus())){
            queryWrapper.eq("status",picture.getStatus());
        }
        if (StringUtils.hasText(picture.getTitle())){
            queryWrapper.like("title",picture.getTitle());
        }
        queryWrapper.orderByDesc("create_time");

        IPage<Picture> picturePage = pictureService.page(page, queryWrapper);


        return R.ok().data("picturePage",picturePage);
    }

    @ApiOperation("根据id删除照片")
    @DeleteMapping("/deletePicture/{pictureId}")
    public R deletePicture(@PathVariable String pictureId){
        pictureService.removeById(pictureId);
        return R.ok();
    }

    @ApiOperation("添加照片")
    @PostMapping("/addPicture")
    public R addPicture(@Validated @RequestBody Picture picture){

        pictureService.save(picture);
        return R.ok();
    }

    @ApiOperation("修改类型")
    @PostMapping("/updatePictureInfo")
    public R updatePictureInfo(@Validated @RequestBody Picture picture){

        pictureService.updateById(picture);
        return R.ok();
    }

    @ApiOperation("根据Id查询照片")
    @GetMapping("/getPictureById/{pictureId}")
    public R getPictureById(@PathVariable String pictureId){

        Picture picture = pictureService.getById(pictureId);

        if (picture==null){
            throw  new LZLException(20001,"该照片不存在");
        }
        return R.ok().data("picture",picture);

    }

}

