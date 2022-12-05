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
@ApiModel(value = "用户实体类：User", description = "<span style='color:red;'>描述:</span>&nbsp;&nbsp;对应数据库中的user表")
public class User extends BaseEntity implements Serializable
{
    @TableId(value = "uid", type = IdType.AUTO)
    @ApiModelProperty(value = "用户ID")
    private Integer uid;
    @ApiModelProperty(value = "用户名")
    private String username;
    @ApiModelProperty(value = "密码")
    private String password;
    @ApiModelProperty(value = "电话")
    private String phone;
    @ApiModelProperty(value = "电子邮箱")
    private String email;
    @ApiModelProperty(value = "性别")
    private Integer gender;
    @ApiModelProperty(value = "头像")
    private String avatar;
}

