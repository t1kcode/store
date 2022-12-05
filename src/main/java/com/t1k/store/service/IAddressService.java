package com.t1k.store.service;

import com.t1k.store.entity.Address;

import java.util.List;

/** 处理收货地址数据的业务层接口 */
public interface IAddressService
{
    /**
     * 添加收货地址
     * @param uid 当前登录的用户id
     * @param username 当前登录的用户名
     * @param address 收货地址数据
     */
    void addAddress(Integer uid, String username, Address address);

    /**
     * 获取收货地址列表
     * @param uid 当前登录的用户id
     * @param username 当前登录的用户名
     * @return 返回收货地址列表
     */
    List<Address> getAddressList(Integer uid, String username);

    /**
     * 设置默认收货地址
     * @param aid 收货地址id
     * @param uid 当前登录的用户id
     * @param username 当前登录的用户名
     */
    void setDefault(Integer aid, Integer uid, String username);

    /**
     * 删除收货地址
     * @param aid 收货地址id
     * @param uid 当前登录的用户id
     * @param username 当前登录的用户名
     */
    void delAddress(Integer aid, Integer uid, String username);

    /**
     * 获取收货地址信息
     * @param aid 收货地址id
     * @param uid 当前登录的用户id
     * @param username 当前登录的用户名
     * @return 返回收货地址信息
     */
    Address getAddress(Integer aid, Integer uid, String username);

    /**
     * 修改收货地址
     * @param uid 当前登录的用户id
     * @param username 当前登录的用户名
     * @param address 收货地址信息
     */
    void modAddress(Integer uid, String username, Address address);

}
