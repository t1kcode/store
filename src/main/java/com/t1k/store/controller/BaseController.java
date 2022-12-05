package com.t1k.store.controller;

import com.t1k.store.controller.ex.*;
import com.t1k.store.service.ex.*;
import com.t1k.store.entity.JsonResult;
import org.apache.tomcat.util.http.fileupload.impl.FileUploadIOException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpSession;
import java.util.Objects;

public class BaseController
{
    public final static int OK = 200;

    /**
     * 从HttpSession对象中获取uid
     * @param session HttpSession对象
     * @return 当前登录的用户的id
     */
    protected final Integer getUidFromSession(HttpSession session)
    {
        if(Objects.isNull(session.getAttribute("uid"))) throw new SessionEmptyException("用户未登录");
        return Integer.valueOf(session.getAttribute("uid").toString());
    }

    /**
     * 从HttpSession对象中获取用户名
     * @param session HttpSession对象
     * @return 当前登录的用户名
     */
    protected final String getUsernameFromSession(HttpSession session)
    {
        if(Objects.isNull(session.getAttribute("username"))) throw new SessionEmptyException("用户未登录");
        return session.getAttribute("username").toString();
    }

    /**
     * 从HttpSession对象中获取用户头像
     * @param session HttpSession对象
     * @return 当前登录的用户名
     */
    protected final String getAvatarFromSession(HttpSession session)
    {
        if(Objects.isNull(session.getAttribute("avatar"))) throw new SessionEmptyException("用户未登录");
        return session.getAttribute("avatar").toString();
    }

    @ExceptionHandler({ServiceException.class, FileUploadException.class, SessionEmptyException.class, CodeNotMatchException.class})
    public JsonResult<Void> handleException(Throwable e)
    {
        JsonResult<Void> result = new JsonResult<>();

        if(e instanceof InsertException){
            result.setState(4000);
            result.setMessage("数据插入失败");
        } else if(e instanceof UpdateException){
            result.setState(4001);
            result.setMessage("数据更新失败");
        } else if(e instanceof DeleteException){
            result.setState(4002);
            result.setMessage("数据删除失败");
        } else if(e instanceof UsernameDuplicateException){
            result.setState(5000);
            result.setMessage("用户名重复");
        } else if(e instanceof UserNotFoundException){
            result.setState(5001);
            result.setMessage("用户不存在");
        } else if(e instanceof PasswordNotMatchException){
            result.setState(5002);
            result.setMessage("密码不匹配");
        } else if(e instanceof CodeNotMatchException){
            result.setState(5003);
            result.setMessage("验证码错误");
        } else if(e instanceof AddressCountLimitException){
            result.setState(6001);
            result.setMessage("地址数量已超限制");
        } else if(e instanceof AddressNotFoundException){
            result.setState(6002);
            result.setMessage("地址不存在");
        } else if(e instanceof AccessDeniedException){
            result.setState(6003);
            result.setMessage("访问被拒绝");
        } else if(e instanceof ProductNotFoundException){
            result.setState(6004);
            result.setMessage("商品不存在");
        } else if(e instanceof CartNotFoundException){
            result.setState(6005);
            result.setMessage("购物车数据不存在");
        } else if(e instanceof OrderNotFoundException){
            result.setState(6006);
            result.setMessage("订单数据不存在");
        } else if(e instanceof FileEmptyException){
            result.setState(7001);
            result.setMessage("文件为空");
        } else if(e instanceof FileSizeException){
            result.setState(7002);
            result.setMessage("文件大小超出限制");
        } else if(e instanceof FileTypeException){
            result.setState(7003);
            result.setMessage("文件类型不正确");
        } else if(e instanceof FileStateException){
            result.setState(7004);
            result.setMessage("文件状态异常");
        } else if(e instanceof FileUploadIOException){
            result.setState(7005);
            result.setMessage("文件上传失败");
        } else if(e instanceof SessionEmptyException){
            result.setState(8000);
            result.setMessage("用户未登录");
        } else if(e instanceof ServiceException){
            result.setState(9000);
            result.setMessage("未知错误");
        }
        return result;
    }
}
