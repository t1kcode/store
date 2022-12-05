package com.t1k.store.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/** 商品数据的实体类 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "商品实体类：Product", description = "<span style='color:red;'>描述:</span>&nbsp;&nbsp;对应数据库中的product表")
public class Product extends BaseEntity implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "商品ID")
    private Integer id;
    @ApiModelProperty(value = "分类ID")
    private Integer categoryId;
    @ApiModelProperty(value = "商品系列")
    private String itemType;
    @ApiModelProperty(value = "商品标题")
    private String title;
    @ApiModelProperty(value = "商品卖点")
    private String sellPoint;
    @ApiModelProperty(value = "商品单价")
    private Long price;
    @ApiModelProperty(value = "商品数量")
    private Integer num;
    @ApiModelProperty(value = "商品图片路径")
    private String image;
    @ApiModelProperty(value = "商品状态：1-上架，2-下架，3-删除")
    private Integer status;
    @ApiModelProperty(value = "显示优先级，数字越大优先级越高")
    private Integer priority;
}
