package com.lzl.blogservice.service;

import com.lzl.blogservice.entity.Type;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lzl.blogservice.entity.Vo.TypeFrontVo;

import java.util.List;

/**
 * <p>
 * 博客类型 服务类
 * </p>
 *
 * @author LiZeLin
 * @since 2022-11-16
 */
public interface TypeService extends IService<Type> {

    List<TypeFrontVo> getTypeList();
}
