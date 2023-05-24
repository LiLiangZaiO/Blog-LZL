package com.lzl.ossservice.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.lzl.ossservice.service.OssService;
import com.lzl.ossservice.utils.ConstantPropertiesUtils;
import com.lzl.ossservice.utils.LZLUtils;

import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {
/*

    // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
    private static String endpoint = ConstantPropertiesUtils.END_POINT;
    // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
    private static String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;
    private static String accessKeySecret = ConstantPropertiesUtils.ACCESS_KEY_SECRET;
    // 填写Bucket名称，例如examplebucket。
    private static String bucketName = ConstantPropertiesUtils.BUCKET_NAME;

*/

    @Override
    public String uploadFileAvatar(MultipartFile file, String prefixName) {
        // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
        String endpoint = ConstantPropertiesUtils.END_POINT;
        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.ACCESS_KEY_SECRET;
        // 填写Bucket名称，例如examplebucket。
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        try {
            InputStream inputStream = null;
            try {
                inputStream = file.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //获取文件名称 --后面前端模板会自动改名成file.png
            String filename = file.getOriginalFilename();

            //在文件名称里面添加随机唯一的值 --去掉UUID的 - 横杠
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            filename = uuid+filename;

            //把文件按照日期进行分类
            String datePath = new DateTime().toString("yyyy/MM");

            //最终拼接
            filename = prefixName+"/"+datePath+"/"+filename;

            //解决中文乱码
            String encodeUrl = LZLUtils.encodeUrl(filename);
            // 创建PutObject请求。
            ossClient.putObject(bucketName, encodeUrl, inputStream);

            String url = "https://"+bucketName+"."+endpoint+"/"+encodeUrl;
            return url;

        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        }finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return null;
    }

    @Override
    public Boolean deleteOssFile(String url) {
        // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
        String endpoint = ConstantPropertiesUtils.END_POINT;
        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.ACCESS_KEY_SECRET;
        // 填写Bucket名称，例如examplebucket。
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        try {
            // 删除文件或目录。如果要删除目录，目录必须为空。
            String objectName = url.substring(url.lastIndexOf(endpoint) + endpoint.length()+1);
            ossClient.deleteObject(bucketName, objectName);
            //判断文件是否存在 来返回是否删除成功！！
            boolean exist = ossClient.doesObjectExist(bucketName, objectName);
            return !exist;
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return false;
    }
}
