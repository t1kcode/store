package com.t1k.store.controller.page;

import com.t1k.store.controller.BaseController;
import com.t1k.store.controller.ex.*;
import com.t1k.store.entity.JsonResult;
import com.t1k.store.entity.User;
import com.t1k.store.service.IUserService;
import io.swagger.annotations.*;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/user")
@Api(tags = "用户相关接口描述")
public class UserController extends BaseController
{
    @Resource
    IUserService service;

    @ApiOperation(value = "修改密码", notes = "<span style='color:red;'>描述:</span>&nbsp;&nbsp;用于用户修改密码")
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 4001, message = "数据更新失败"),
            @ApiResponse(code = 5001, message = "用户不存在"),
            @ApiResponse(code = 5002, message = "原密码不匹配"),
            @ApiResponse(code = 8000, message = "用户未登录")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "oldPassword", value = "原密码", required = true),
            @ApiImplicitParam(name = "newPassword", value = "新密码", required = true),
    })
    @PostMapping("change_password")
    public JsonResult<Void> changePassword(HttpSession session, String oldPassword, String newPassword)
    {
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        service.changePassword(uid, username, oldPassword, newPassword);
        return new JsonResult<>(OK);
    }

    @ApiOperation(value = "获取用户信息", notes = "<span style='color:red;'>描述:</span>&nbsp;&nbsp;用于获取用户信息，返回User对象")
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 5001, message = "用户不存在"),
            @ApiResponse(code = 8000, message = "用户未登录")
    })
    @GetMapping("get_perInfo")
    public JsonResult<User> getPerInfo(HttpSession session)
    {
        Integer uid = getUidFromSession(session);
        User temp = service.getByUid(uid);
        User user = new User();
        user.setUsername(temp.getUsername());
        user.setPhone(temp.getPhone());
        user.setEmail(temp.getEmail());
        user.setGender(temp.getGender());
        return new JsonResult<>(OK, user);
    }

    @ApiOperation(value = "修改用户资料", notes = "<span style='color:red;'>描述:</span>&nbsp;&nbsp;用于用户修改个人资料")
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 4001, message = "数据更新失败"),
            @ApiResponse(code = 5001, message = "用户不存在"),
            @ApiResponse(code = 8000, message = "用户未登录")
    })
    @PostMapping("change_info")
    public JsonResult<Void> changeInfo(HttpSession session, @RequestBody User user)
    {
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        service.changeInfo(uid, username, user);
        return new JsonResult<>(OK);
    }

    /** 头像文件大小的上限值(10MB) */
    private static final int AVATAR_MAX_SIZE = 10 * 1024 * 1024;
    /** 允许上传的头像的文件类型 */
    private static final List<String> AVATAR_TYPES = new ArrayList<String>(){{
        add("image/jpeg");
        add("image/jpg");
        add("image/png");
        add("image/bmp");
        add("image/gif");
    }};

    @Value("${web.upload-path}")
    private String uploadPath;

    @ApiOperation(value = "修改用户头像", notes = "<span style='color:red;'>描述:</span>&nbsp;&nbsp;用于用户上传个人头像，返回新头像的路径")
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 4001, message = "数据更新失败"),
            @ApiResponse(code = 5001, message = "用户不存在"),
            @ApiResponse(code = 7001, message = "文件为空"),
            @ApiResponse(code = 7002, message = "文件大小超出限制"),
            @ApiResponse(code = 7003, message = "文件类型不正确"),
            @ApiResponse(code = 7004, message = "文件状态异常"),
            @ApiResponse(code = 7005, message = "文件上传失败"),
            @ApiResponse(code = 8000, message = "用户未登录")
    })
    @ApiImplicitParam(name = "file", value = "头像文件", required = true)
    @PostMapping("change_avatar")
    public JsonResult<String> changeAvatar(HttpSession session, MultipartFile file)
    {
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        if(Objects.isNull(file)){
            throw new FileEmptyException("文件为空");
        }
        if(file.isEmpty()){
            throw new FileEmptyException("文件不能为空");
        }
        if(file.getSize() > AVATAR_MAX_SIZE){
            throw new FileSizeException("上传文件不能超过" + (AVATAR_MAX_SIZE / 1024) + "KB");
        }
        if(!AVATAR_TYPES.contains(file.getContentType())){
            throw new FileTypeException("不支持该文件类型");
        }
//        String parent = session.getServletContext().getRealPath("upload") + '\\' + username;
        String parent = uploadPath + username;

        File dir = new File(parent);
        if(!dir.exists()){
            dir.mkdirs();
        }
        // 保存的头像文件的文件名
        String originalFilename = file.getOriginalFilename();
        if(StringUtils.isBlank(originalFilename)) throw new FileTypeException("文件类型异常");
        int index = originalFilename.lastIndexOf(".");
        String suffix = originalFilename.substring(index); // 获取文件后缀
        String filename = UUID.randomUUID().toString() + suffix;

        // 创建文件对象，表示保存的头像文件
        File dest = new File(dir, filename);
        // 执行保存头像文件
        try {
            file.transferTo(dest);
        } catch (IllegalStateException e) {
            throw new FileStateException("文件状态异常，可能文件已被移动或删除");
        } catch (IOException e) {
            throw new FileUploadIOException("上传文件时读写错误，请稍后重新尝试");
        }

        // 头像路径
        String avatar = "/upload/" + username + File.separator + filename;
        // 从Session中获取uid和username
        // 将头像写入到数据库中
        service.changeAvatar(uid, username, avatar);
        session.setAttribute("avatar", avatar); // 更新session中的头像路径
        return new JsonResult<>(OK, avatar);
    }
}
