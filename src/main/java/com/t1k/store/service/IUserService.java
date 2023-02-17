package com.t1k.store.service;

import com.t1k.store.entity.User;

import java.util.List;

/** 处理用户数据的业务层接口 */
public interface IUserService
{
    /**
     * 用户注册
     * @param user 用户数据对象
     */
    void reg(User user);

    /**
     * 修改密码
     * @param uid 当前登录的用户id
     * @param username 用户名
     * @param oldPassword 原密码
     * @param newPassword 新密码
     */
    public void changePassword(Integer uid, String username, String oldPassword, String newPassword);

    /**
     * 获取当前登录的用户的信息
     * @param uid 当前登录的用户id
     * @return 当前登录的用户信息
     */
    User getByUid(Integer uid);

    /**
     * 获取当前登录的用户的信息
     * @param username 当前登录的用户名
     * @return 当前登录的用户信息
     */
    User getByUsername(String username);

    /**
     * 修改用户资料
     * @param uid 当前登录的用户id
     * @param username 当前登录的用户名
     * @param user 用户新的数据
     */
    void changeInfo(Integer uid, String username, User user);

    /**
     * 修改用户头像
     * @param uid 当前登录的用户id
     * @param username 当前登录的用户名
     * @param avatar 用户新头像的路径
     */
    void changeAvatar(Integer uid, String username, String avatar);

    /**
     * 判断用户是否存在
     * @param uid 当前登录的用户id
     * @param username 当前登录的用户名
     */
    void judgeUser(Integer uid, String username);

    /**
     * 判断密码是否相同
     * @param uid 当前登录的用户id
     * @param password1 原密码
     * @param password2 新密码
     */
    void judgePassword(Integer uid, String password1, String password2);

    /**
     * 删除用户
     * @param uid 用户id
     * @param username 用户名
     */
    void deleteUser(Integer uid, String username);

    /**
     * 获取所有用户
     * @param uid 用户id
     * @param username 用户名
     */
    List<User> getUsers(Integer uid, String username);

    /**
     * 获取所有用户数量
     * @param uid 用户id
     * @param username 用户名
     */
    Integer getUCount(Integer uid, String username);
}
