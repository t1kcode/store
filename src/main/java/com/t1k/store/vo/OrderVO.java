package com.t1k.store.vo;

import com.t1k.store.entity.OrderItem;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "订单数据VO类：OrderVO")
public class OrderVO implements Serializable
{
    @ApiModelProperty(value = "订单ID")
    private Integer oid;
    @ApiModelProperty(value = "收货地址ID")
    private Integer aid;
    @ApiModelProperty(value = "收货人姓名")
    private String recvName;
    @ApiModelProperty(value = "电话")
    private String phone;
    @ApiModelProperty(value = "收货人所在省")
    private String provinceName;
    @ApiModelProperty(value = "收货人所在市")
    private String cityName;
    @ApiModelProperty(value = "收货人所在区")
    private String areaName;
    @ApiModelProperty(value = "详细地址")
    private String address;
    @ApiModelProperty(value = "订单状态：0-未支付，1-已支付，2-已取消，3-已关闭，4-已完成")
    private Integer status;
    @ApiModelProperty(value = "下单时间")
    private Date orderTime;
    @ApiModelProperty(value = "支付时间")
    private Date payTime;
    @ApiModelProperty(value = "订单总价")
    private Long totalPrice;
    @ApiModelProperty(value = "邮政编码")
    private String zip;
    @ApiModelProperty(value = "商品详细信息")
    List<OrderItem> orderItems;
}
