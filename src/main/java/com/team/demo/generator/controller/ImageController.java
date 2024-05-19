package com.team.demo.generator.controller;


import com.team.demo.config.Result;
import com.team.demo.generator.dao.CommentMapper;
import com.team.demo.generator.dao.ImageMapper;
import com.team.demo.generator.entity.Comment;
import com.team.demo.generator.entity.Image;
import com.team.demo.generator.service.ImageService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ky
 * @since 2024年04月23日
 */
@RestController
@RequestMapping("secure/file")
public class ImageController {
    @javax.annotation.Resource
    public ImageMapper imageMapper;


    @javax.annotation.Resource
    public ImageService imageService;

    @javax.annotation.Resource
    public CommentMapper commentMapper;



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
            return Result.success(image.getId());
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("23","异常错误");
        }


    }

    /**
     * 获取文件的流
     */
    @GetMapping("/image")
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

    @GetMapping("/userimage")
    public List<Image> userImage(@RequestParam Integer id)
    {
        /*List<Image> imageL = imageMapper.findAllImages();
        return imageService.around(longitude,latitude,imageL,radius);*/
        return  imageMapper.findUserImages(id);
    }

    @GetMapping("/comments")
    public List<Comment> getImageComments(@RequestParam Integer imageid)
    {
        return commentMapper.findComment(imageid);
    }

    @GetMapping("/user/comments")
    public List<Comment> getUserComments(@RequestParam Integer userid)
    {
        return commentMapper.findUserComment(userid);
    }

    /*@GetMapping("/findImageByObject")
    public Image findImage(@PathVariable Integer id){
        return imageMapper.selectById(id);
    }*/

    @DeleteMapping("/comment/delete")
    public Result<?> DeleteComment(@RequestParam Integer commentId,HttpServletRequest request)
    {
        Integer userId = (Integer) request.getAttribute("id");
        Comment comment = commentMapper.selectById(commentId);
        if(comment == null)
        {
            return Result.error("404","未找到路径");
        }
        if(comment.getUserid() == userId || userId == 1)
        {
            commentMapper.deleteById(commentId);
            return Result.success();
        }
        else
        {
            return Result.error("403","无权限");
        }
    }

    @PutMapping("/comment/update")
    public Result<?> UpdateComment(@RequestParam Integer commentId,@RequestParam Integer userId,@RequestParam String newContain)
    {
        Comment comment = commentMapper.selectById(commentId);
        if(comment == null)
        {
            return Result.error("404","未找到路径");
        }
        comment.setContain(newContain);
        if(comment.getUserid() == userId || userId == 1)
        {
            commentMapper.updateById(comment);
            return Result.success();
        }
        else
        {
            return Result.error("403","无权限");
        }
    }

    @PostMapping("/comment/add")
    public Result<?> addComment(@RequestBody Comment comment)
    {
        //System.out.println(Contain);
        /*Comment comment = new Comment();
        comment.setContain(Contain);
        comment.setUserid(userId);
        comment.setImageid(imageId);*/
        /*System.out.println(comment.getUserid());
        System.out.println(comment.getContain());
        System.out.println(comment.getImageid());*/
        commentMapper.insert(comment);
        return Result.success();
    }

    @DeleteMapping("/image/delete")
    public Result<?> deleteImage(@RequestParam Integer imageId, HttpServletRequest request)
    {
        Integer userId = (Integer) request.getAttribute("id");
        Image image = imageMapper.selectById(imageId);
        if(image == null)
        {
            return Result.error("404","未找到路径");
        }

        if(Objects.equals(image.getUserId(), userId) || userId == 1)
        {
            List<Comment> comments = commentMapper.findComment(imageId);
            for(Comment comment : comments)
            {
                commentMapper.deleteById(comment);
            }
            imageMapper.deleteById(image);
            // 删除本地文件
            try {
                Path path = Paths.get(image.getPath()); // 假设Image对象中有一个方法getFilePath()返回文件的存储路径
                if (Files.exists(path)) {
                    Files.delete(path);
                }
            } catch (Exception e) {
                return Result.error("500", "文件删除失败");
            }

            return Result.success();
        }
        else {
            return Result.error("403","无权限");
        }

    }



}

