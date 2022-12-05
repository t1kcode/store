package com.t1k.store.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.t1k.store.entity.User;
import com.t1k.store.mapper.UserMapper;
import com.t1k.store.service.IUserService;
import com.t1k.store.service.ex.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Objects;

/** 处理用户数据的业务层实现类 */
@Service
public class UserServiceImpl implements IUserService
{
    @Resource
    UserMapper userMapper;

    @Resource
    PasswordEncoder encoder;

    @Override
    public void reg(User user)
    {
        // 判断该用户名是否已被注册
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, user.getUsername())
               .select(User::getUid);
        if(!ObjectUtils.isEmpty(userMapper.selectOne(wrapper))) throw new UsernameDuplicateException("该用户名已被注册");
        // 日志
        user.setCreatedUser(user.getUsername());
        user.setModifiedUser(user.getUsername());
        Date date = new Date();
        user.setCreatedTime(date);
        user.setModifiedTime(date);
        // 加密密码
        user.setPassword(encoder.encode(user.getPassword()));
        int rows = userMapper.insert(user);
        if(rows != 1) throw new InsertException("插入时产生未知异常");
    }

    @Override
    public void changePassword(Integer uid, String username, String oldPassword, String newPassword)
    {
        User user = userMapper.selectById(uid);
        if(ObjectUtils.isEmpty(user)) throw new UserNotFoundException("该用户不存在");
        else
        {
            // 校验原密码是否一致
            JudgePassword(uid, oldPassword, user.getPassword());
            LambdaUpdateWrapper<User> wrapper = new LambdaUpdateWrapper<>();
            wrapper.eq(User::getUid, uid)
                   .set(User::getPassword, encoder.encode(newPassword));
            int row = userMapper.update(null, wrapper);
            if(row != 1) throw new UpdateException("更新数据时发生异常");
        }
    }

    @Override
    public User getByUid(Integer uid)
    {
        User user = userMapper.selectById(uid);
        if(ObjectUtils.isEmpty(user)) throw new UserNotFoundException("该用户不存在");
        return user;
    }

    @Override
    public User getByUsername(String username)
    {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, username);
        User user = userMapper.selectOne(wrapper);
        if(ObjectUtils.isEmpty(user)) throw new UserNotFoundException("该用户不存在");
        return user;
    }

    @Override
    public void changeInfo(Integer uid, String username, User user)
    {
        JudgeUser(uid, username);
        user.setUid(uid);
        user.setCreatedUser(user.getUsername());
        user.setModifiedUser(user.getUsername());
        Date date = new Date();
        user.setCreatedTime(date);
        user.setModifiedTime(date);
        int row = userMapper.updateById(user);
        if(row != 1) throw new UpdateException("更新数据时发生异常");
    }

    @Override
    public void changeAvatar(Integer uid, String username, String avatar)
    {
        JudgeUser(uid, username);
        LambdaUpdateWrapper<User> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(User::getUid, uid)
               .set(User::getAvatar, avatar);
        int row = userMapper.update(null, wrapper);
        if(row != 1) throw new UpdateException("更新数据时发生异常");
    }

    @Override
    public void JudgeUser(Integer uid, String username)
    {
        final LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUid, uid)
               .eq(User::getUsername, username);
        if(ObjectUtils.isEmpty(userMapper.selectOne(wrapper))) throw new UserNotFoundException("该用户不存在");
    }

    @Override
    public void JudgePassword(Integer uid, String password1, String password2)
    {
        if(ObjectUtils.isEmpty(password2)){
            LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
            wrapper.select(User::getPassword)
                   .eq(User::getUid, uid);
            User user = userMapper.selectOne(wrapper);
            if(!encoder.matches(password1, user.getPassword())) throw new PasswordNotMatchException("密码不匹配");
        }
        else
        {
            if(!encoder.matches(password1, password2)) throw new PasswordNotMatchException("密码不匹配");
        }
    }
}
