package com.t1k.store.controller.page;

import com.t1k.store.controller.BaseController;
import com.t1k.store.entity.JsonResult;
import com.t1k.store.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/user")
@Api(tags = "用户信息相关接口描述")
public class AccountController extends BaseController
{
    @ApiOperation(value = "设置用户信息", notes = "<span style='color:red;'>描述:</span>&nbsp;&nbsp;用于获取用户的用户ID、用户名、头像，返回User对象 ")
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 8000, message = "用户未登录")
    })
    @GetMapping("info")
    public JsonResult<User> info(HttpSession session)
    {
        User user = new User();
        user.setUid(getUidFromSession(session));
        user.setUsername(getUsernameFromSession(session));
        user.setAvatar(getAvatarFromSession(session));
        user.setRole(getRoleFromSession(session));
        // 因为没有默认头像，所以用户再没有设置头像之前请求头像都会抛出异常，前端会显示用户未登录
        // 解决办法给每位用户设置一个默认头像
        return new JsonResult<>(OK, user);
    }
}
