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

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
/* 收货地址数据的实体类 */
@ApiModel(value = "地址实体类：Address", description = "<span style='color:red;'>描述:</span>&nbsp;&nbsp;对应数据库中的address表")
public class Address extends BaseEntity implements Serializable
{
    @TableId(value = "aid", type = IdType.AUTO)
    @ApiModelProperty(value = "地址ID")
    private Integer aid;
    @ApiModelProperty(value = "用户ID")
    private Integer uid;
    @ApiModelProperty(value = "收货人姓名")
    private String name;
    @ApiModelProperty(value = "省-名称")
    private String provinceName;
    @ApiModelProperty(value = "省-行政代号")
    private String provinceCode;
    @ApiModelProperty(value = "市-名称")
    private String cityName;
    @ApiModelProperty(value = "市-行政代号")
    private String cityCode;
    @ApiModelProperty(value = "区-名称")
    private String areaName;
    @ApiModelProperty(value = "区-行政代号")
    private String areaCode;
    @ApiModelProperty(value = "邮政编码")
    private String zip;
    @ApiModelProperty(value = "详细地址")
    private String address;
    @ApiModelProperty(value = "电话")
    private String phone;
    @ApiModelProperty(value = "固话")
    private String tel;
    @ApiModelProperty(value = "标签")
    private String tag;
    @ApiModelProperty(value = "是否为默认地址，1-默认，0-非默认")
    private Integer isDefault;
}








