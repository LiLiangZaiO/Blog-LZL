package com.lzl.ossservice.service;

import org.springframework.web.multipart.MultipartFile;

public interface OssService {
    String uploadFileAvatar(MultipartFile file, String prefixName);

    Boolean deleteOssFile(String url);
}
