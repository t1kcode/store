package com.t1k.store.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "收藏商品实体类：Collect", description = "<span style='color:red;'>描述:</span>&nbsp;&nbsp;对应数据库中的collect表")
public class Collect extends BaseEntity
{
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "主键ID")
    private Integer id;
    @ApiModelProperty(value = "用户ID")
    private Integer uid;
    @ApiModelProperty(value = "商品ID")
    private Integer pid;
    @ApiModelProperty(value = "商品图片")
    private String image;
    @ApiModelProperty(value = "商品标题")
    private String title;
    @ApiModelProperty(value = "商品单价")
    private Long price;
    @ApiModelProperty(value = "商品状态：1-收藏中，0-未收藏")
    private Integer status;
}
