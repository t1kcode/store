package com.t1k.store.service;

import com.t1k.store.entity.Order;
import com.t1k.store.vo.OrderVO;

import java.util.List;

/** 处理订单数据的业务层接口 */
public interface IOrderService
{
    /**
     * 创建订单
     * @param uid 当前登录的用户id
     * @param username 当前登录的用户名
     * @param aid 收货地址id
     * @param cids 购物车数据id
     * @return 订单数据信息
     */
    Integer createOrderByC(Integer uid, String username, Integer aid, List<Integer> cids);

    Integer createOrderByP(Integer uid, String username, Integer aid, Integer pid, Integer num);

    /**
     * 删除订单
     * @param uid 当前登录的用户id
     * @param username 当前登录的用户名
     * @param oid 订单id
     */
    void deleteOrder(Integer uid, String username, Integer oid);

    /**
     * 获取订单数据列表
     * @param uid 当前登录的用户id
     * @param username 当前登录的用户名
     * @return 订单数据列表
     */
    List<OrderVO> getOrderVOs(Integer uid, String username);

    /**
     * 根据订单状态获取订单信息
     * @param uid 当前登录的用户id
     * @param username 当前登录的用户名
     * @param status 订单的状态：0-未支付，1-已支付，2-已取消，3-已关闭，4-已完成
     * @return 订单信息列表
     */
    List<OrderVO> getOrderVOsByS(Integer uid, String username, List<Integer> status);

    /**
     * 根据订单id获取订单信息
     * @param uid 当前登录的用户id
     * @param username 当前登录的用户名
     * @param oid 订单id
     * @return 订单信息
     */
    OrderVO getOrderVO(Integer uid, String username, Integer oid);

    /**
     * 设置订单状态
     * @param uid 当前登录的用户id
     * @param username 当前登录的用户名
     * @param oid 订单id
     * @param status 订单的状态：0-未支付，1-已支付，2-已取消，3-已关闭，4-已完成
     */
    void setStatus(Integer uid, String username, Integer oid, Integer status);

    /**
     * 在支付界面获取订单信息
     * @param uid 当前登录的用户id
     * @param username 当前登录的用户名
     * @param oid 订单id
     * @return 订单信息
     */
    OrderVO getOrderInfo(Integer uid, String username, Integer oid);
}
