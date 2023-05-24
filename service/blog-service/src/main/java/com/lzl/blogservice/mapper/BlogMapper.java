package com.lzl.blogservice.mapper;

import com.lzl.blogservice.entity.Blog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lzl.blogservice.entity.Vo.ArchivesFrontVo;

import java.util.List;

/**
 * <p>
 * 博客 Mapper 接口
 * </p>
 *
 * @author LiZeLin
 * @since 2022-11-16
 */
public interface BlogMapper extends BaseMapper<Blog> {

    Boolean increaseViewCount(String blogId);

    Boolean thumbsUpBlog(String blogId, Integer judge);

    List<ArchivesFrontVo> getArchiveList();
}
