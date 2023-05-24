package com.lzl.ossservice.controller;

import com.lzl.common_utils.domain.R;
import com.lzl.ossservice.entity.Picture;
import com.lzl.ossservice.service.OssService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Api(tags = "照片上传")
@RestController
@RequestMapping("/oss")
@Secured({"ROLE_ADMIN","ROLE_PHOTO"})
public class OssController {

    @Autowired
    private OssService ossService;

    @PostMapping("/uploadPicture")
    public R uploadOssFile(MultipartFile file){
        //获取上传文件，MultipartFile
        //根据数据库的teacher等表，需要返回oss的路径
        String prefixName="ZhaoPianQiang";
        String url = ossService.uploadFileAvatar(file,prefixName);
        return R.ok().data("url",url);
    }

    /**
     * 本来是deleteMapping的，但是我认为只能把 url 拼接到路径上，在后台@PathVariable获取
     *  因为是一个链接，包含着 特殊符号，// 会被转义，甚至路径都匹配不上
     *  所以目前换上post请求，但是不能直接@RequestBody Stirng url来获取，会出现特殊字符乱码，则需要转换，转换后发现后面多了一个等号，
     *  分析原因：携带的参数中：key是链接，value是空值 （不知道怎么解决），最后换成实体类Picture来接收，当然前台也需要封装到PictureInfo中，返回
     * @param picture
     * @return
     */
    @PostMapping("/deletePicture")
    public R deleteOssFile(@RequestBody Picture picture){
        String url = picture.getAddress();
        Boolean b = ossService.deleteOssFile(url);
        if (b) {
            return R.ok();
        }else {
            return R.error().message("删除失败！");
        }
    }

}
