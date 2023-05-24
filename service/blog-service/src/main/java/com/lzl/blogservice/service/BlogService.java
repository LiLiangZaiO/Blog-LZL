package com.lzl.blogservice.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzl.blogservice.entity.Blog;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lzl.blogservice.entity.Vo.ArchivesFrontVo;
import com.lzl.blogservice.entity.Vo.BlogDetailFrontVo;
import com.lzl.blogservice.entity.Vo.BlogFrontVo;
import com.lzl.blogservice.entity.Vo.BlogInfoVo;

import java.util.List;

/**
 * <p>
 * 博客 服务类
 * </p>
 *
 * @author LiZeLin
 * @since 2022-11-16
 */
public interface BlogService extends IService<Blog> {

    List<BlogInfoVo> getBlogList();

    IPage<Blog> pageQuery(Page<Blog> page, BlogInfoVo blogInfoVo);

    List<BlogInfoVo> getBlogVoList(List<Blog> blogList);

    List<BlogFrontVo> getBlogFrontVoList(List<Blog> blogList);

    BlogDetailFrontVo getBlogDetailFront(String blogId);

    Boolean increaseViewCount(String blogId);

    Boolean thumbsUpBlog(String blogId, Integer judge);

    List<ArchivesFrontVo> getArchiveList();

    Boolean addBlogInfo(Blog blog);

    List<BlogFrontVo> getAllBlogFrontVo();

    BlogFrontVo getBlogEsById(String blogId);

    List<BlogDetailFrontVo> getBlogDetailList();
}
