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

/** 购物车数据的实体类 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "购物车数据实体类：Cart", description = "<span style='color:red;'>描述:</span>&nbsp;&nbsp;对应数据库中的cart表")
public class Cart extends BaseEntity implements Serializable {
    @TableId(value = "cid", type = IdType.AUTO)
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
}
