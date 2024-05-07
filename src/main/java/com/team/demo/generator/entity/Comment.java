package com.team.demo.generator.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author ky
 * @since 2024年04月08日
 */
@Getter
@Setter
  @Accessors(chain = true)
  @TableName("comment")
public class Comment implements Serializable {

    //private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

    @TableField("userid")
    private Integer userid;

    @TableField("imageid")
    private Integer imageid;

    @TableField("contain")
    private String contain;



    /*@TableField("distance")
    private Integer distance;*/


}
