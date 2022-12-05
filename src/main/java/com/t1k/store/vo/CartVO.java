package com.t1k.store.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/** 购物车数据的Value Object类 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "购物车数据VO类：CartVO")
public class CartVO implements Serializable
{
    @ApiModelProperty(value = "购物车数据ID")
    private Integer cid;
    @ApiModelProperty(value = "用户ID")
    private Integer uid;
    @ApiModelProperty(value = "商品ID")
    private Integer pid;
    @ApiModelProperty(value = "商品单价")
    private Long price;
    @ApiModelProperty(value = "商品数量")
    private Integer num;
    @ApiModelProperty(value = "商品标题")
    private String title;
    @ApiModelProperty(value = "商品真实价格")
    private Long realPrice;
    @ApiModelProperty(value = "商品图片")
    private String image;
}
