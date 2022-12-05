package com.t1k.store.service;

import com.t1k.store.vo.CartVO;

import java.util.List;

/** 处理购物车数据的业务层接口 */
public interface ICartService
{
    /**
     * 将商品添加到购物车中
     * @param uid 当前登录的用户id
     * @param username 当前登录的用户名
     * @param pid 商品id
     * @param amount 商品数量
     */
    Integer addToCart(Integer uid, String username, Integer pid, Integer amount);

    /**
     * 获取购物车数据信息
     * @param uid 当前登录的用户id
     * @param username 当前登录的用户名
     * @return 购物车数据信息列表
     */
    List<CartVO> getCartVOs(Integer uid, String username);

    /**
     * 删除购物车数据
     * @param uid 当前登录的用户id
     * @param username 当前登录的用户名
     * @param cid 购物车数据id
     */
    void delCart(Integer uid, String username, Integer cid);

    /**
     * 删除选择的购物车数据
     * @param uid 当前登录的用户id
     * @param username 当前登录的用户名
     * @param cids 购物车数据id
     */
    void selDelCart(Integer uid, String username, List<Integer> cids);

    /**
     * 改变购物车中商品的数量
     * @param uid 当前登录的用户id
     * @param username 当前登录的用户名
     * @param cid 购物车数据id
     * @param num 商品数量
     */
    void changeCartNum(Integer uid, String username, Integer cid, Integer num);

    /**
     * 根据购物车数据id获取购物车信息
     * @param uid 当前登录的用户id
     * @param username 当前登录的用户名
     * @param cids 购物车数据id
     * @return 购物车数据信息列表
     */
    List<CartVO> getCartVOsByCids(Integer uid, String username, List<Integer> cids);
}
