package com.lzl.blogservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzl.blogservice.entity.Blog;
import com.lzl.blogservice.entity.Type;
import com.lzl.blogservice.entity.Vo.ArchivesFrontVo;
import com.lzl.blogservice.entity.Vo.BlogDetailFrontVo;
import com.lzl.blogservice.entity.Vo.BlogFrontVo;
import com.lzl.blogservice.entity.Vo.BlogInfoVo;
import com.lzl.blogservice.mapper.BlogMapper;
import com.lzl.blogservice.service.BlogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzl.blogservice.service.TypeService;
import com.lzl.feign.clients.UserClient;
import com.lzl.feign.entity.User;
import com.lzl.servicebase.exceptionHandler.LZLException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 博客 服务实现类
 * </p>
 *
 * @author LiZeLin
 * @since 2022-11-16
 */
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {

    @Autowired
    private UserClient userClient;
    @Autowired
    private TypeService typeService;



    @Override
    public List<BlogInfoVo> getBlogList() {
        List<BlogInfoVo> blogInfoVoList = new ArrayList<>();

        List<Blog> blogList = baseMapper.selectList(null);
        for (Blog blog : blogList) {
            BlogInfoVo blogInfoVo = new BlogInfoVo();
            BeanUtils.copyProperties(blog,blogInfoVo);
            Type type = typeService.getById(blog.getTypeId());
            blogInfoVo.setTypeName(type.getTypeName());
            //远程调用 查询用户
            User user = userClient.queryById(blog.getUserId());
            blogInfoVo.setNickname(user.getNickname());
            blogInfoVoList.add(blogInfoVo);
        }

        return blogInfoVoList;
    }

    @Override
    public IPage<Blog> pageQuery(Page<Blog> page, BlogInfoVo blogInfoVo) {
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();

        if (blogInfoVo == null){
            queryWrapper.orderByDesc("update_time");
            return baseMapper.selectPage(page, queryWrapper);
        }


        String title = blogInfoVo.getTitle();
        String status = blogInfoVo.getStatus();

        String nickname = blogInfoVo.getNickname();
        String typeName = blogInfoVo.getTypeName();


        if (StringUtils.hasText(title)){
            queryWrapper.like("title",title);
        }

        if (StringUtils.hasText(status)){
            queryWrapper.eq("status",status);
        }

        if (StringUtils.hasText(nickname)){
            //远程调用
            List<String> userIds = userClient.getIdsByNickName(nickname);
            queryWrapper.in("user_id",userIds);
        }

        if (StringUtils.hasText(typeName)){
            Type type = typeService.getOne(new QueryWrapper<Type>().eq("type_name", typeName));
            queryWrapper.eq("type_id",type.getId());
        }

        //排序
        queryWrapper.orderByDesc("update_time");
        return baseMapper.selectPage(page, queryWrapper);
    }

    @Override
    public List<BlogInfoVo> getBlogVoList(List<Blog> blogList) {
        List<BlogInfoVo> blogInfoVoList = new ArrayList<>();
        for (Blog blog : blogList) {
            BlogInfoVo blogInfoVo = new BlogInfoVo();
            BeanUtils.copyProperties(blog,blogInfoVo);
            Type type = typeService.getById(blog.getTypeId());
            blogInfoVo.setTypeName(type.getTypeName());
            //远程调用 查询用户
            User user = userClient.queryById(blog.getUserId());
            blogInfoVo.setNickname(user.getNickname());
            blogInfoVoList.add(blogInfoVo);
        }

        return blogInfoVoList;
    }

    /**
     * 前台分页 博客
     * 封装相关数据给前台博客的vo类
     * @param blogList
     * @return
     */
    @Override
    public List<BlogFrontVo> getBlogFrontVoList(List<Blog> blogList) {
        List<BlogFrontVo> BlogFrontVoList = new ArrayList<>();

        for (Blog blog : blogList) {

            BlogFrontVo blogFrontVo = new BlogFrontVo();
            BeanUtils.copyProperties(blog,blogFrontVo);

            Type type = typeService.getById(blog.getTypeId());
            blogFrontVo.setTypeName(type.getTypeName());

            //远程调用 查询用户
            User user = userClient.queryById(blog.getUserId());
            blogFrontVo.setNickname(user.getNickname());
            blogFrontVo.setAvatar(user.getAvatar());

            BlogFrontVoList.add(blogFrontVo);
        }

        return BlogFrontVoList;
    }

    /**
     * 根据 博客Id 查询 前台博客的详情信息
     * @param blogId
     * @return
     */
    @Override
    public BlogDetailFrontVo getBlogDetailFront(String blogId) {
        BlogDetailFrontVo blogDetail = new BlogDetailFrontVo();

        Blog blog = baseMapper.selectById(blogId);

        BeanUtils.copyProperties(blog,blogDetail);

        //远程调用 查询用户
        User user = userClient.queryById(blog.getUserId());
        blogDetail.setNickname(user.getNickname());
        blogDetail.setAvatar(user.getAvatar());

        //TODO 生成版权
        blogDetail.setCopyright(1L);

        return blogDetail;
    }

    //浏览量
    @Override
    public Boolean increaseViewCount(String blogId) {
        return baseMapper.increaseViewCount(blogId);
    }

    //点赞
    @Override
    public Boolean thumbsUpBlog(String blogId, Integer judge) {
        //judge点赞的增加、减少（前端传来的值）只能是 1或者-1
        if (judge==1||judge==-1){
            return baseMapper.thumbsUpBlog(blogId,judge);
        }
        return false;
    }

    /**
     * 查询所有归档的列表
     * @return
     */
    @Override
    public List<ArchivesFrontVo> getArchiveList() {
        List<ArchivesFrontVo> archivesFrontVoList = baseMapper.getArchiveList();

        return archivesFrontVoList;
    }

    @Override
    public Boolean addBlogInfo(Blog blog) {
        Integer count = baseMapper.selectCount(new QueryWrapper<Blog>().eq("id", blog.getId()));

        if (count>0){
            throw new LZLException(20001,"该博客已存在");
        }

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        String userId = userClient.getUserIdByUsername(username);
        blog.setUserId(userId);
        int insert = baseMapper.insert(blog);
        if (insert<=0){
            return false;
        }
        return true;
    }

    @Override
    public List<BlogFrontVo> getAllBlogFrontVo() {
        List<Blog> blogList = baseMapper.selectList(new QueryWrapper<Blog>().eq("status","1"));

        List<BlogFrontVo> blogFrontVoList = getBlogFrontVoList(blogList);

        return blogFrontVoList;

    }

    @Override
    public BlogFrontVo getBlogEsById(String blogId) {
        Blog blog = baseMapper.selectById(blogId);
        BlogFrontVo blogFrontVo = new BlogFrontVo();
        BeanUtils.copyProperties(blog,blogFrontVo);

        Type type = typeService.getById(blog.getTypeId());
        blogFrontVo.setTypeName(type.getTypeName());

        //远程调用 查询用户
        User user = userClient.queryById(blog.getUserId());
        blogFrontVo.setNickname(user.getNickname());
        blogFrontVo.setAvatar(user.getAvatar());


        return blogFrontVo;
    }

    @Override
    public List<BlogDetailFrontVo> getBlogDetailList() {
        List<Blog> blogList = baseMapper.selectList(new QueryWrapper<Blog>().eq("status","1"));
        List<BlogDetailFrontVo> blogDetailList = new ArrayList<>();
        for (Blog blog : blogList) {
            BlogDetailFrontVo blogDetailFrontVo = new BlogDetailFrontVo();
            BeanUtils.copyProperties(blog,blogDetailFrontVo);

            //远程调用 查询用户
            User user = userClient.queryById(blog.getUserId());
            blogDetailFrontVo.setNickname(user.getNickname());
            blogDetailFrontVo.setAvatar(user.getAvatar());

            Type type = typeService.getById(blog.getTypeId());
            blogDetailFrontVo.setTypeName(type.getTypeName());


            //TODO 生成版权
            blogDetailFrontVo.setCopyright(1L);
            blogDetailList.add(blogDetailFrontVo);
        }
        return blogDetailList;
    }
}
