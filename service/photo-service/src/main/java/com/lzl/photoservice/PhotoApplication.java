package com.lzl.photoservice;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@ComponentScan("com.lzl")
@MapperScan("com.lzl.photoservice.mapper")
@SpringBootApplication
public class PhotoApplication {

    public static void main(String[] args) {
        SpringApplication.run(PhotoApplication.class,args);
    }

}
