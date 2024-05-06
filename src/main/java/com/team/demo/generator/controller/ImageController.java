package com.team.demo.generator.controller;


import com.team.demo.config.Result;
import com.team.demo.generator.dao.ImageMapper;
import com.team.demo.generator.entity.Image;
import com.team.demo.generator.service.ImageService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ky
 * @since 2024年04月23日
 */
@RestController
@RequestMapping("/file")
public class ImageController {
    @javax.annotation.Resource
    public ImageMapper imageMapper;


    @javax.annotation.Resource
    public ImageService imageService;

    @PostMapping("/upload")
    public Result<?> uploadFile(@RequestParam(value = "file",required = false) MultipartFile file, @RequestParam double latitude,
                                @RequestParam double longitude, @RequestParam Integer id){
        // 判断文件是否为空
        if(file == null || file.isEmpty()){
            return Result.error("404","没收到你的图片");
        }
        // 获取传过来的文件名字
        String OriginalFilename=file.getOriginalFilename();

        // 为了防止重名覆盖，获取系统时间戳+原始文件的后缀名
        String fileName= null;
        fileName = System.currentTimeMillis()+"."+OriginalFilename.substring(OriginalFilename.lastIndexOf(".")+1);
        Image image = new Image();
        image.setLongitude(longitude);
        image.setLatitude(latitude);
        image.setUserId(id);
        image.setPath("F:\\java项目\\lbs-master\\assets\\images\\"+fileName);

        // 设置保存地址（这里是转义字符）
        //1.后台保存位置
        String path = "F:\\java项目\\lbs-master\\assets\\images\\";
        File dest=new File(path+fileName);
        // 判断文件是否存在
        if(!dest.getParentFile().exists()){
            // 不存在就创建一个
            dest.getParentFile().mkdirs();
        }
        try {
            // 后台上传
            file.transferTo(dest);
            imageMapper.insert(image);
            return Result.success( "文件上传成功");
        }catch (Exception e){
            e.printStackTrace();
            return Result.error("23","异常错误");
        }


    }

    /**
     * 获取文件的流
     */
    @GetMapping("/secure/image")
    public ResponseEntity<Resource> getImage(@RequestParam Integer imageId) throws Exception {
        Image image = imageMapper.selectById(imageId);
        Path path = Paths.get(image.getPath());
        Resource resource = new UrlResource(path.toUri());
        if (resource.exists() || resource.isReadable()) {
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .body(resource);
        } else {
            throw new RuntimeException("Could not read the file!");
        }
    }


    /*@GetMapping("/locate")
    public List<Image> locateLinkImage(@RequestParam double latitude,
                                       @RequestParam double longitude,@RequestParam double radius)
    {
        //return imageMapper.selectImagesWithinRadius(latitude,longitude,radius);
        //return new List<Image>();
    }*/


    @GetMapping("/findImage/{id}")
    public Image findImage(@PathVariable Integer id){
        return imageMapper.selectById(id);
    }


    @GetMapping("/locate")
    public List<Image> locateLinkImage(@RequestParam double latitude,
                                       @RequestParam double longitude,@RequestParam double radius)
    {
        List<Image> imageL = imageMapper.findAllImages();
        return imageService.around(longitude,latitude,imageL,radius);
    }

    /*@GetMapping("/findImageByObject")
    public Image findImage(@PathVariable Integer id){
        return imageMapper.selectById(id);
    }*/
}

