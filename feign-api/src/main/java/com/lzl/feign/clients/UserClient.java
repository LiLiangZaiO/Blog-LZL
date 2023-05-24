package com.lzl.feign.clients;

import com.lzl.feign.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(value = "userservice")
public interface UserClient {

    @GetMapping("/user/getUserById/{id}")
    User queryById(@PathVariable("id")String id);

    @GetMapping("/user/getIdsByNickName/{nickname}")
    List<String> getIdsByNickName(@PathVariable("nickname") String nickname);

    @GetMapping("/user/getUserIdByUsername/{username}")
    String getUserIdByUsername(@PathVariable("username") String username);


}
