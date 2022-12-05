package com.t1k.store.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/** 省/市/区数据的实体类 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("dict_district")
@ApiModel(value = "城市名称实体类：District", description = "<span style='color:red;'>描述:</span>&nbsp;&nbsp;对应数据库中的dict_district表")
public class District implements Serializable
{
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "主键ID")
    private Integer id;
    @ApiModelProperty(value = "上级编号")
    private String parent;
    @ApiModelProperty(value = "自身编号")
    private String code;
    @ApiModelProperty(value = "城市名称")
    private String name;
}
