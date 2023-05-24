package com.lzl.feign.clients;

import com.lzl.feign.entity.BlogFrontVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(value = "blogservice")
public interface BlogClient {

    @GetMapping("/front/blog/uploadDocument")
    List<BlogFrontVo> getAllBlogFrontVo();

    @GetMapping("/front/blog/getBlogEsById/{blogId}")
    BlogFrontVo getBlogEsById(@PathVariable("blogId") String blogId);

}
