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

/** 订单中的商品数据 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "订单实体类：OrderItem", description = "<span style='color:red;'>描述:</span>&nbsp;&nbsp;对应数据库中的order_item表")
public class OrderItem extends BaseEntity implements Serializable
{
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "主键ID")
    private Integer id;
    @ApiModelProperty(value = "订单ID")
    private Integer oid;
    @ApiModelProperty(value = "商品ID")
    private Integer pid;
    @ApiModelProperty(value = "商品标题")
    private String title;
    @ApiModelProperty(value = "商品图片")
    private String image;
    @ApiModelProperty(value = "商品单价")
    private Long price;
    @ApiModelProperty(value = "商品数量")
    private Integer num;
}
