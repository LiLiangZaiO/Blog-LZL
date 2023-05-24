package com.lzl.blogservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lzl.blogservice.entity.Blog;
import com.lzl.blogservice.entity.Type;
import com.lzl.blogservice.entity.Vo.TypeFrontVo;
import com.lzl.blogservice.mapper.TypeMapper;
import com.lzl.blogservice.service.BlogService;
import com.lzl.blogservice.service.TypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 博客类型 服务实现类
 * </p>
 *
 * @author LiZeLin
 * @since 2022-11-16
 */
@Service
public class TypeServiceImpl extends ServiceImpl<TypeMapper, Type> implements TypeService {

    @Autowired
    private BlogService blogService;

    @Override
    public List<TypeFrontVo> getTypeList() {
        QueryWrapper<Type> queryWrapper = new QueryWrapper<>();

//        queryWrapper.orderByDesc("create_time");
//        queryWrapper.last("limit 6");

        List<Type> typeList = baseMapper.selectList(queryWrapper);

        List<TypeFrontVo> frontVoList = new ArrayList<>();

        for (Type type : typeList) {
            TypeFrontVo typeFrontVo = new TypeFrontVo();
            BeanUtils.copyProperties(type,typeFrontVo);
            int typeCount = blogService.count(new QueryWrapper<Blog>().eq("type_id", type.getId()).eq("status","1"));
            typeFrontVo.setTypeCount((long) typeCount);
            frontVoList.add(typeFrontVo);

        }
        return frontVoList;
    }
}
