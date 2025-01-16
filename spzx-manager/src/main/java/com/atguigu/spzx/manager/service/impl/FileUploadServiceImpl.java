package com.atguigu.spzx.manager.service.impl;

import cn.hutool.core.date.DateUtil;

import com.atguigu.spzx.manager.properties.MinioProperties;
import com.atguigu.spzx.manager.service.FileUploadService;

import io.minio.*;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
;import java.util.Date;
import java.util.UUID;

/**
 * @author ：zxl
 * @Description:
 * @ClassName: FileUploadServiceImpl
 * @date ：2024/04/17 20:32
 */
@Service
@Slf4j
//@ConfigurationProperties("spzx-minio")
public class FileUploadServiceImpl implements FileUploadService {
    @Resource
    private MinioProperties minioProperties;
//    @Value("${spzx.minio.minioProperties}")
//    private String minioProperties;
    @Override
    public String upload(MultipartFile file) {
        try {
            // 创建一个Minio的客户端对象
            MinioClient minioClient = MinioClient.builder()
                    .endpoint(minioProperties.getEndpointUrl())
                    .credentials(minioProperties.getAccessKey(), minioProperties.getSecretKey())
                    .build();

            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(minioProperties.getBucketName()).build());

            // 如果不存在，那么此时就创建一个新的桶
            if (!found) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(minioProperties.getBucketName()).build());
            } else {  // 如果存在打印信息
                log.info("Bucket 'spzx-bucket' already exists.");
            }
            //获取上传的文件名
                //1)每个上传文件名唯一  uuid
                //2)根据当前日期对上传文件进行分组
            String dateDir = DateUtil.format(new Date(), "yyyyMMdd");
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            String fileName = dateDir+"/"+uuid+file.getOriginalFilename();
                //20230801/443e1e772bef482c95be28704bec58a901.jpg
            //文件上传
            minioClient.putObject(PutObjectArgs.builder().bucket(minioProperties.getBucketName())
                    .object(fileName)
                    .stream(file.getInputStream(), file.getSize(), -1)
                    .build());
            //partSize:-1  表示不知道文件大小

            //获取上传文件在minio路径
            return minioProperties.getEndpointUrl()+"/"+minioProperties.getBucketName()+"/"+fileName;


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

