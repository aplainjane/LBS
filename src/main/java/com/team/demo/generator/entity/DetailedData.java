package com.team.demo.generator.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal; // 引入 BigDecimal 类

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 详细数据表实体类
 * </p>
 *
 * @author ky
 * @since 2024年05月03日
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("DetailedData")
public class DetailedData implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "code", type = IdType.AUTO)
    private String code;

    /**
     * 名称
     */
    @TableField("name")
    private String name;

    /**
     * 面积
     */
    @TableField("size")
    private BigDecimal size; // 使用 BigDecimal 类存储面积数据


    /**
     * 保护对象
     */
    @TableField("locate")
    private String locate;

    /**
     * 保护对象
     */
    @TableField("protectObject")
    private String protectObject;

    @TableField("imageid")
    private int imageid;

    /**
     * 类型
     */
    @TableField("type")
    private String type;

    /**
     * 级别
     */
    @TableField("level")
    private String level;

    /**
     * 设置时间
     */
    @TableField("settime")
    private String setTime;

    /**
     * 所属街道
     */
    @TableField("department")
    private String department;

    @TableField(exist = false)
    double longitude = 0;

    @TableField(exist = false)
    double latitude= 0;

}
