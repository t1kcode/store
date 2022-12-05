package com.t1k.store.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/** 订单数据的实体类 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("order_user") // 不能使用order为表名
@ApiModel(value = "订单实体类：Order", description = "<span style='color:red;'>描述:</span>&nbsp;&nbsp;对应数据库中的order_user表")
public class Order extends BaseEntity implements Serializable
{
    @TableId(value = "oid", type = IdType.AUTO)
    @ApiModelProperty(value = "订单ID")
    private Integer oid;
    @ApiModelProperty(value = "用户ID")
    private Integer uid;
    @ApiModelProperty(value = "收货地址ID")
    private Integer aid;
    @ApiModelProperty(value = "收货人姓名")
    private String recvName;
    @ApiModelProperty(value = "收货人电话")
    private String recvPhone;
    @ApiModelProperty(value = "收货人所在省")
    private String recvProvince;
    @ApiModelProperty(value = "收货人所在市")
    private String recvCity;
    @ApiModelProperty(value = "收货人所在区")
    private String recvArea;
    @ApiModelProperty(value = "收货人详细地址")
    private String recvAddress;
    @ApiModelProperty(value = "订单总价")
    private Long totalPrice;
    @ApiModelProperty(value = "订单状态：0-未支付，1-已支付，2-已取消，3-已关闭，4-已完成")
    private Integer status;
    @ApiModelProperty(value = "下单时间")
    private Date orderTime;
    @ApiModelProperty(value = "支付时间")
    private Date payTime;
}
