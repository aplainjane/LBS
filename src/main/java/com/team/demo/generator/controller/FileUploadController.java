package com.team.demo.generator.controller;

import com.team.demo.config.Result;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@CrossOrigin
@RestController
@RequestMapping("/file")
public class FileUploadController {
    @PostMapping("/upload")
    public Result<?> uploadFile(@RequestParam(value = "file",required = false) MultipartFile file){
        // 判断文件是否为空
        if(file.isEmpty()){
            return Result.error("404","not found");
        }
        // 获取传过来的文件名字
        String OriginalFilename=file.getOriginalFilename();
        // 为了防止重名覆盖，获取系统时间戳+原始文件的后缀名
        String fileName=System.currentTimeMillis()+"."+OriginalFilename.substring(OriginalFilename.lastIndexOf(".")+1);
        // 设置保存地址（这里是转义字符）
        //1.后台保存位置
        String path = "F:\\java项目\\lbs-master\\src\\assets\\images\\";
        File dest=new File(path+fileName);
        // 判断文件是否存在
        if(!dest.getParentFile().exists()){
            // 不存在就创建一个
            dest.getParentFile().mkdirs();
        }
        try {
            // 后台上传
            file.transferTo(dest);
            return Result.success( "文件上传成功");
        }catch (Exception e){
            e.printStackTrace();
            return Result.error("23","异常错误");
        }


    }

}