package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.manager.service.FileUploadService;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author ：zxl
 * @Description: 文件上传
 * @ClassName: FileUploadController
 * @date ：2024/04/17 20:31
 */
@RestController
@RequestMapping("/admin/system")
public class FileUploadController {
    @Resource
    private FileUploadService fileUploadService;
    @PostMapping("/fileUpload")
    public Result<String> fileUpload(@RequestParam("file") MultipartFile file){
        //1.获取上传的文件
        //2.调用service的方法，返回minio路径
        String url = fileUploadService.upload(file);
        return Result.build(url, ResultCodeEnum.SUCCESS);
    }


}
