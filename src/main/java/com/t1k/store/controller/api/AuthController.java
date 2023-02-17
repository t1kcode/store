package com.t1k.store.controller.api;

import com.google.code.kaptcha.Constants;
import com.t1k.store.controller.BaseController;
import com.t1k.store.controller.ex.CodeNotMatchException;
import com.t1k.store.controller.ex.SessionEmptyException;
import com.t1k.store.entity.JsonResult;
import com.t1k.store.entity.User;
import com.t1k.store.service.IUserService;
import io.swagger.annotations.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Objects;

@RestController
@RequestMapping("/api/auth")
@Api(tags = "用户验证相关接口描述")
public class AuthController extends BaseController
{
    @Resource
    IUserService userService;

    @ApiOperation(value = "用户注册", notes = "<span style='color:red;'>描述:</span>&nbsp;&nbsp;用于用户注册")
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 4000, message = "数据插入失败"),
            @ApiResponse(code = 5000, message = "用户名重复"),
    })
    @PostMapping("reg")
    public JsonResult<Void> reg(@RequestBody User user)
    {
        userService.reg(user);
        return new JsonResult<>(OK);
    }

    @ApiOperation(value = "用户登录", notes = "<span style='color:red;'>描述:</span>&nbsp;&nbsp;用于用户登录")
    @RequestMapping(value = "login", method = {RequestMethod.GET, RequestMethod.POST})
    public void login() {}

    @ApiOperation(value = "登录成功", notes = "<span style='color:red;'>描述:</span>&nbsp;&nbsp;表示登录成功，并将用户信息插入session中")
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功"),
    })
    @PostMapping("login-success")
    public JsonResult<Void> loginSuccess(@ApiParam(hidden = true) HttpSession session)
    {
        SecurityContext context = SecurityContextHolder.getContext();
        String username = context.getAuthentication().getName();
        User user = userService.getByUsername(username);
        session.setAttribute("username", user.getUsername());
        session.setAttribute("uid", user.getUid());
        session.setAttribute("avatar", user.getAvatar());
        session.setAttribute("role", user.getRole());
        return new JsonResult<>(OK);
    }

    @ApiOperation(value = "登录失败", notes = "<span style='color:red;'>描述:</span>&nbsp;&nbsp;表示登录失败")
    @ApiResponses({
            @ApiResponse(code = 300, message = "登录失败"),
    })
    @PostMapping("login-failure")
    public JsonResult<Void> loginFailure()
    {
        return new JsonResult<>(300, "登陆失败");
    }

    @ApiOperation(value = "未登录", notes = "<span style='color:red;'>描述:</span>&nbsp;&nbsp;验证用户是否登录")
    @ApiResponses({
            @ApiResponse(code = 301, message = "未验证，请登录"),
    })
    @GetMapping("login-deny")
    public JsonResult<Void> loginDeny()
    {
        return new JsonResult<>(301, "未验证，请登录");
    }

    @ApiOperation(value = "退出登录", notes = "<span style='color:red;'>描述:</span>&nbsp;&nbsp;用于用户退出登录，并清除session中存储的用户信息")
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功"),
    })
    @RequestMapping(value = "logout", method = {RequestMethod.GET, RequestMethod.POST})
    public JsonResult<Void> logout(@ApiParam(hidden = true) HttpSession session)
    {
        session.removeAttribute("username");
        session.removeAttribute("uid");
        session.removeAttribute("avatar");
        session.removeAttribute("role");
        return new JsonResult<>(OK);
    }

    @ApiOperation(value = "退出登录成功", notes = "<span style='color:red;'>描述:</span>&nbsp;&nbsp;表示用户退出登录成功")
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功"),
    })
    @GetMapping("logout-success")
    public JsonResult<Void> logoutSuccess()
    {
        return new JsonResult<>(OK);
    }

    @ApiOperation(value = "验证记住我", notes = "<span style='color:red;'>描述:</span>&nbsp;&nbsp;用于验证用户是否使用记住我")
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 5001, message = "用户不存在"),
            @ApiResponse(code = 9000, message = "没有使用记住我")
    })
    @GetMapping("remember")
    public JsonResult<Void> remember(@ApiParam(hidden = true) HttpSession session)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(Objects.isNull(authentication)) throw new SessionEmptyException("没有remember");
        String username = authentication.getName();
        User user = userService.getByUsername(username);
        session.setAttribute("username", user.getUsername());
        session.setAttribute("uid", user.getUid());
        session.setAttribute("avatar", user.getAvatar());
        return new JsonResult<>(OK);
    }

    @ApiOperation(value = "验证验证码", notes = "<span style='color:red;'>描述:</span>&nbsp;&nbsp;用于验证用户验证码是否填写正确")
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 5003, message = "验证码错误")
    })
    @ApiImplicitParam(name = "code", value = "验证码", required = true)
    @PostMapping("code")
    public JsonResult<Void> code(@ApiParam(hidden = true) HttpSession session, String code)
    {
        String iCode = session.getAttribute(Constants.KAPTCHA_SESSION_KEY).toString();
        if(!iCode.equals(code)) throw new CodeNotMatchException("验证码错误");
        return new JsonResult<>(OK);
    }

    @ApiOperation(value = "验证密码", notes = "<span style='color:red;'>描述:</span>&nbsp;&nbsp;用于验证用户修改密码时原密码是否正确")
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 5002, message = "原密码不正确"),
            @ApiResponse(code = 9000, message = "用户未登录")
    })
    @ApiImplicitParam(name = "password", value = "原密码", required = true)
    @PostMapping("judge_password")
    public JsonResult<Void> judgePassword(@ApiParam(hidden = true) HttpSession session, String password)
    {
        Integer uid = getUidFromSession(session);
        userService.judgePassword(uid, password, null);
        return new JsonResult<>(OK);
    }
}
