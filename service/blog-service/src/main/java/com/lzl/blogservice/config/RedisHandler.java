package com.lzl.blogservice.config;

import com.lzl.blogservice.entity.Vo.BlogDetailFrontVo;
import com.lzl.blogservice.service.BlogService;
import com.lzl.common_utils.domain.R;
import com.lzl.common_utils.utils.JsonUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RedisHandler implements InitializingBean {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private BlogService blogService;

    @Override
    public void afterPropertiesSet() throws Exception {

        /**
         * 缓存预热
         */
        List<BlogDetailFrontVo>  blogDetailList = blogService.getBlogDetailList();
        for (BlogDetailFrontVo blogDetailFrontVo : blogDetailList) {
            String json = JsonUtils.toString(R.ok().data("blogDetail",blogDetailFrontVo));
            redisTemplate.opsForValue().set("blogDetail:id:"+blogDetailFrontVo.getId(),json);
        }
    }
}
