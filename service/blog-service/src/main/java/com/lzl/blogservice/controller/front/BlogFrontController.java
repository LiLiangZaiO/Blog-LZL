package com.lzl.blogservice.controller.front;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.benmanes.caffeine.cache.Cache;
import com.lzl.blogservice.entity.Blog;
import com.lzl.blogservice.entity.Vo.ArchivesFrontVo;
import com.lzl.blogservice.entity.Vo.BlogDetailFrontVo;
import com.lzl.blogservice.entity.Vo.BlogFrontVo;
import com.lzl.blogservice.service.BlogService;
import com.lzl.common_utils.domain.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/front/blog")
public class BlogFrontController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private Cache<Long,BlogDetailFrontVo> BlogDetailFrontCache;

    /**
     * 远程调用 根据Id返回对应的ES博客数据
     * @param blogId
     * @return
     */
    @GetMapping("/getBlogEsById/{blogId}")
    public BlogFrontVo getBlogEsById(@PathVariable("blogId") String blogId){
        return blogService.getBlogEsById(blogId);
    }


    /**
     * 远程调用，让es模块生成文档
     * @return
     */
    @GetMapping("/uploadDocument")
    public List<BlogFrontVo> getAllBlogFrontVo(){
        return blogService.getAllBlogFrontVo();
    }


    @PostMapping("/pageFrontBlog/{current}/{limit}")
    public R pageFrontBlog(@PathVariable long current,
                           @PathVariable long limit){

        Page<Blog> page = new Page<>(current, limit);
        IPage<Blog> blogPage = blogService.page(page,new QueryWrapper<Blog>().orderByDesc("create_time").eq("status","1"));

        List<Blog> blogList = blogPage.getRecords();
        List<BlogFrontVo> BlogFrontVoList = blogService.getBlogFrontVoList(blogList);
        blogPage.setRecords(null);
        return R.ok().data("blogPage",blogPage).data("BlogList",BlogFrontVoList);
    }

    @GetMapping("/pageFrontBlogByTypeId/{current}/{limit}")
    public R pageFrontBlogByTypeId(@PathVariable long current,
                                   @PathVariable long limit,
                                   @RequestParam("typeId") String typeId){

        Page<Blog> page = new Page<>(current, limit);
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status","1");
        if (StringUtils.hasText(typeId)){
            queryWrapper.eq("type_id",typeId);
        }
        queryWrapper.orderByDesc("create_time");
        IPage<Blog> blogPage = blogService.page(page,queryWrapper);

        List<Blog> blogList = blogPage.getRecords();
        List<BlogFrontVo> BlogFrontVoList = blogService.getBlogFrontVoList(blogList);
        blogPage.setRecords(null);
        return R.ok().data("blogPage",blogPage).data("BlogList",BlogFrontVoList);
    }

    @GetMapping("/getBlogDetailFront/{blogId}")
    public R getBlogDetailFront(@PathVariable String blogId){

        BlogDetailFrontVo blogDetailFrontVo = BlogDetailFrontCache.get(Long.valueOf(blogId), key -> blogService.getBlogDetailFront(String.valueOf(key)));


        return R.ok().data("blogDetail",blogDetailFrontVo);
    }

    /**
     * 浏览量
     * @param blogId
     * @return
     */
    @PostMapping("/increaseViewCount/{blogId}")
    public R increaseViewCount(@PathVariable String blogId){
        Boolean result = blogService.increaseViewCount(blogId);
        return result?R.ok():R.error().message("浏览量异常！");
    }

    /**
     * 点赞功能
     * @param blogId
     * @param judge
     * @return
     */
    @PostMapping("/thumbsUp/{blogId}/{judge}")
    public R thumbsUpBlog(@PathVariable String blogId,@PathVariable Integer judge){
        Boolean result = blogService.thumbsUpBlog(blogId,judge);
        if(result){
            QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
            queryWrapper.select("thumbs_count").eq("id",blogId);
            Blog blog = blogService.getOne(queryWrapper);
            return R.ok().data("thumbsCount",blog.getThumbsCount());
        }else {
            return R.error().message("点赞异常！");
        }

    }

    /**
     * 根据访问量获取热门博客
     * 注意：需要访问的是 已经发布的博客文章  .eq("status","1");
     * @return
     */
    @GetMapping("/getHotBlogList")
    public R getHotBlogList(){
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id","title").eq("status","1").orderByDesc("view_count").last("limit 6");
        List<Blog> blogList = blogService.list(queryWrapper);
        return R.ok().data("HotBlogList",blogList);
    }


    /**
     * 查询所有归档的列表
     * @return
     */
    @GetMapping("/getArchiveList")
    public R getArchiveList(){

        List<ArchivesFrontVo> archivesFrontVoList =  blogService.getArchiveList();
        int blogCount = blogService.count(new QueryWrapper<Blog>().eq("status","1"));

        return R.ok().data("archiveList",archivesFrontVoList).data("blogCount",blogCount);
    }

}
