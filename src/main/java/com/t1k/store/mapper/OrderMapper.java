package com.t1k.store.mapper;

import com.github.yulichang.base.MPJBaseMapper;
import com.t1k.store.entity.Order;

/** 处理订单用户数据操作的持久层接口 */
public interface OrderMapper extends MPJBaseMapper<Order>
{
//    @Select("select a.oid, a.aid, a.recv_name as recvName, a.total_price as totalPrice, a.status, a.order_time as orderTime, a.pay_time as payTime, " +
//            "b.image, b.title, b.price, b.num, " +
//            "c.zip, c.address, c.province_name as provinceName, c.phone, c.city_name as cityName, c.area_name as areaName " +
//            "from (order_user a " +
//            "left join order_item b on a.oid = b.oid) " +
//            "left join address c on a.aid = c.aid " +
//            "where a.uid=#{uid} " +
//            "order by a.status ASC, a.created_time DESC")
//    List<OrderVO> getOrderVOs(Integer uid);
}
