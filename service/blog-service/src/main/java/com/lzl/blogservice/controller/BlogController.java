package com.lzl.blogservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzl.blogservice.constants.MqConstants;
import com.lzl.blogservice.entity.Blog;
import com.lzl.blogservice.entity.Vo.BlogInfoVo;
import com.lzl.blogservice.service.BlogService;
import com.lzl.common_utils.domain.R;
import com.lzl.servicebase.exceptionHandler.LZLException;
import io.swagger.annotations.ApiOperation;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 博客 前端控制器
 * </p>
 *
 * @author LiZeLin
 * @since 2022-11-16
 */
@RestController
@RequestMapping("/blog")
@Secured({"ROLE_ADMIN","ROLE_BLOG"})
public class BlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private RabbitTemplate rabbitTemplate;


    @ApiOperation(value = "分页条件查询博客表数据")
    @PostMapping("/pageBlogCondition/{current}/{limit}")
    public R pageBlogCondition(@PathVariable long current,
                               @PathVariable long limit,
                               @RequestBody(required = false) BlogInfoVo blogInfoVo){

        Page<Blog> page = new Page<>(current, limit);
        IPage<Blog> blogPage = blogService.pageQuery(page,blogInfoVo);

        List<Blog> blogList = blogPage.getRecords();
        List<BlogInfoVo> blogInfoVoList = blogService.getBlogVoList(blogList);
        blogPage.setRecords(null);
        return R.ok().data("blogPage",blogPage).data("BlogList",blogInfoVoList);
    }

    @ApiOperation(value = "根据博客Id查询博客数据")
    @GetMapping("/getBlogInfo/{blogId}")
    public R getBlogInfo(@PathVariable String blogId){

        Blog blog = blogService.getById(blogId);

        return R.ok().data("Blog",blog);

    }

    @ApiOperation("修改博客")
    @PostMapping("/updateBlogInfo")
    public R updateBlogInfo(@Validated @RequestBody Blog blog){

        blogService.updateById(blog);
        //发送MQ消息，更新ES
        rabbitTemplate.convertAndSend(MqConstants.BLOG_EXCHANGE,MqConstants.BLOG_INSERT_KEY,blog.getId());
        return R.ok();

    }

    @ApiOperation("根据id删除博客")
    @DeleteMapping("/deleteBlog/{blogId}")
    public R deleteBlog(@PathVariable String blogId){
        blogService.removeById(blogId);
        //发送MQ消息，更新ES
        rabbitTemplate.convertAndSend(MqConstants.BLOG_EXCHANGE,MqConstants.BLOG_DELETE_KEY,blogId);
        return R.ok();
    }

    @ApiOperation("添加博客")
    @PostMapping("/addBlogInfo")
    public R addBlogInfo(@Validated  @RequestBody Blog blog){

        Boolean b = blogService.addBlogInfo(blog);
        if (!b){
            return R.error().message("添加失败!");
        }
        //发送MQ消息，更新ES
        rabbitTemplate.convertAndSend(MqConstants.BLOG_EXCHANGE,MqConstants.BLOG_INSERT_KEY,blog.getId());
        return R.ok();

    }


    /*
    @ApiOperation(value = "查询博客表数据")
    @GetMapping("/getBlogList")
    public R getBlogList(){
        List<BlogInfoVo> blogInfoVoList = blogService.getBlogList();
        return R.ok().data("BlogList",blogInfoVoList);
    }
    */

/*
    @ApiOperation(value = "分页查询博客表数据")
    @GetMapping("/pageBlog/{current}/{limit}")
    public R pageListBlog(@PathVariable("current") Long current,
                          @PathVariable("limit") Long limit){

        Page<Blog> page = new Page<>(current, limit);
        IPage<Blog> blogPage = blogService.page(page, new QueryWrapper<Blog>().orderByDesc("create_time"));
        List<Blog> blogList = blogPage.getRecords();
        List<BlogInfoVo> blogInfoVoList = blogService.getBlogVoList(blogList);
        blogPage.setRecords(null);
        return R.ok().data("blogPage",blogPage).data("BlogList",blogInfoVoList);
    }
    */

}

