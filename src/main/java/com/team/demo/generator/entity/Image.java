package com.team.demo.generator.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@TableName("image")
public class Image {

    @TableField("id")
    private Integer id;

    @TableField("path")
    private String path;

    @TableField("userId")
    private Integer userId;

    @TableField("longitude")
    private double longitude;

    @TableField("latitude")
    private double latitude;
}
