package com.t1k.store.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.t1k.store.entity.Collect;

import java.util.List;

/** 处理收藏商品数据的业务层接口 */
public interface ICollectService
{
    /**
     * 添加收藏商品
     * @param uid 当前登录的用户id
     * @param username 当前登录的用户名
     * @param pid 商品id
     */
    void addCollect(Integer uid, String username, Integer pid);

    /**
     * 获取收藏商品列表
     * @param page 当前分页的信息，包含页码和每页数据条数
     * @param uid 当前登录的用户id
     * @param username 当前登录的用户名
     * @return 收藏商品列表
     */
    List<Collect> getCollects(IPage<Collect> page, Integer uid, String username);

    /**
     * 设置收藏商品状态
     * @param uid 当前登录的用户id
     * @param username 当前登录的用户名
     * @param pid 商品id
     * @param status 收藏商品状态：1-收藏中，0-未收藏
     */
    void setStatus(Integer uid, String username, Integer pid, Integer status);

    /**
     * 获取收藏商品状态
     * @param uid 当前登录的用户id
     * @param username 当前登录的用户名
     * @param pid 商品id
     * @return 收藏商品状态
     */
    Integer getStatus(Integer uid, String username, Integer pid);

    /**
     * 获取在收藏中的收藏商品数量
     * @param uid 当前登录的用户id
     * @param username 当前登录的用户名
     * @return 在收藏中的收藏商品数量
     */
    Integer getCount(Integer uid, String username);
}
