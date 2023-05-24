package com.lzl.photoservice.controller.front;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzl.common_utils.domain.R;
import com.lzl.photoservice.entity.Picture;
import com.lzl.photoservice.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/front/photo")
public class PictureFrontController {

    @Autowired
    private PictureService pictureService;

    @GetMapping("/pagePictureList/{current}/{limit}")
    public R pagePictureList(@PathVariable long current,
                             @PathVariable long limit){

        Page<Picture> page = new Page<>(current,limit);
        IPage<Picture> picturePage = pictureService.page(page, new QueryWrapper<Picture>().eq("status", "1").orderByDesc("create_time"));

        return R.ok().data("picturePage",picturePage);

    }




}
