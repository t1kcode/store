package com.t1k.store.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.t1k.store.entity.Address;
import com.t1k.store.entity.Order;
import com.t1k.store.entity.OrderItem;
import com.t1k.store.entity.Product;
import com.t1k.store.mapper.OrderItemMapper;
import com.t1k.store.mapper.OrderMapper;
import com.t1k.store.mapper.OrderVOMapper;
import com.t1k.store.mapper.ProductMapper;
import com.t1k.store.service.IOrderService;
import com.t1k.store.service.IUserService;
import com.t1k.store.service.ex.InsertException;
import com.t1k.store.service.ex.OrderNotFoundException;
import com.t1k.store.service.ex.ServiceException;
import com.t1k.store.service.ex.UpdateException;
import com.t1k.store.vo.CartVO;
import com.t1k.store.vo.OrderVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements IOrderService
{
    @Resource
    IUserService userService;

    @Resource
    AddressServiceImpl addressService;

    @Resource
    CartServiceImpl cartService;

    @Resource
    OrderMapper orderMapper;

    @Resource
    OrderItemMapper orderItemMapper;

    @Resource
    OrderVOMapper orderVOMapper;

    @Resource
    ProductMapper productMapper;

    @Transactional
    @Override
    public Integer createOrderByC(Integer uid, String username, Integer aid, List<Integer> cids)
    {
        // 根据uid和aid查地址创建order，根据uid和cids查购物车数据
        // 先插入order，再插入orderItem
        // 先查出VO
        List<CartVO> carts = cartService.getCartVOsByCids(uid, username, cids);
        Date date = new Date();
        // 计算商品总价
        Long totalPrice = carts.stream().mapToLong(cart -> (cart.getPrice() * cart.getNum())).sum();

        Address address = addressService.getAddress(aid, uid, username);
        Order order = new Order();
        order.setUid(uid);
        order.setAid(aid);
        order.setRecvName(address.getName());
        order.setRecvPhone(address.getPhone());
        order.setRecvProvince(address.getProvinceName());
        order.setRecvCity(address.getCityName());
        order.setRecvArea(address.getAreaName());
        order.setRecvAddress(address.getAddress());
        order.setTotalPrice(totalPrice);
        order.setStatus(0);
        order.setOrderTime(date);
        // 补全数据：日志
        order.setCreatedUser(username);
        order.setCreatedTime(date);
        order.setModifiedUser(username);
        order.setModifiedTime(date);
        int row = orderMapper.insert(order);
        if(row != 1) throw new InsertException("插入数据时发生异常");

        carts.forEach(cart -> {
            // 创建订单商品数据
            OrderItem item = new OrderItem();
            // 补全数据：setOid(order.getOid())
            item.setOid(order.getOid());
            // 补全数据：pid, title, image, price, num
            item.setPid(cart.getPid());
            item.setTitle(cart.getTitle());
            item.setImage(cart.getImage());
            item.setPrice(cart.getRealPrice());
            item.setNum(cart.getNum());
            // 补全数据：4项日志
            item.setCreatedUser(username);
            item.setCreatedTime(date);
            item.setModifiedUser(username);
            item.setModifiedTime(date);
            // 插入订单商品数据
            int rows2 = orderItemMapper.insert(item);
            if (rows2 != 1) throw new InsertException("插入数据时发生异常");
        });
        return order.getOid();
    }

    @Override
    public Integer createOrderByP(Integer uid, String username, Integer aid, Integer pid, Integer num)
    {
        Date date = new Date();
        Address address = addressService.getAddress(aid, uid, username);
        Product product = productMapper.selectById(pid);
        Long totalPrice = product.getPrice() * num;
        Order order = new Order();
        order.setUid(uid);
        order.setAid(aid);
        order.setRecvName(address.getName());
        order.setRecvPhone(address.getPhone());
        order.setRecvProvince(address.getProvinceName());
        order.setRecvCity(address.getCityName());
        order.setRecvArea(address.getAreaName());
        order.setRecvAddress(address.getAddress());
        order.setTotalPrice(totalPrice);
        order.setStatus(0);
        order.setOrderTime(date);
        // 补全数据：日志
        order.setCreatedUser(username);
        order.setCreatedTime(date);
        order.setModifiedUser(username);
        order.setModifiedTime(date);

        int row = orderMapper.insert(order);
        if(row != 1) throw new InsertException("插入数据时发生异常");

        // 创建订单商品数据
        OrderItem item = new OrderItem();
        // 补全数据：
        item.setOid(order.getOid());
        // 补全数据：pid, title, image, price, num
        item.setPid(pid);
        item.setTitle(product.getTitle());
        item.setImage(product.getImage());
        item.setPrice(product.getPrice());
        item.setNum(num);
        // 补全数据：4项日志
        item.setCreatedUser(username);
        item.setCreatedTime(date);
        item.setModifiedUser(username);
        item.setModifiedTime(date);
        // 插入订单商品数据
        int rows2 = orderItemMapper.insert(item);
        if (rows2 != 1) throw new InsertException("插入数据时发生异常");

        return order.getOid();
    }

    @Override
    public void deleteOrder(Integer uid, String username, Integer oid)
    {
        userService.judgeUser(uid, username);
        LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Order::getOid, oid);
        orderMapper.delete(wrapper);
        LambdaQueryWrapper<OrderItem> wrapper1 = new LambdaQueryWrapper<>();
        wrapper1.eq(OrderItem::getOid, oid);
        orderItemMapper.delete(wrapper1);
    }

    @Override
    public List<OrderVO> getOrderVOs(Integer uid, String username)
    {
        userService.judgeUser(uid, username);
        List<OrderVO> orderVOS = orderVOMapper.getOrderVOs(uid);
        if(ObjectUtils.isEmpty(orderVOS)) throw new ServiceException("订单数据不存在");
        return orderVOS;
    }

    @Override
    public List<OrderVO> getOrders(Integer uid, String username)
    {
        userService.judgeUser(uid, username);
        MPJLambdaWrapper<Order> wrapper = new MPJLambdaWrapper<>();
        wrapper.select(Order::getOid, Order::getAid, Order::getStatus, Order::getRecvName,
                       Order::getTotalPrice, Order::getOrderTime, Order::getPayTime)
               .select(Address::getZip, Address::getAddress, Address::getPhone,
                       Address::getProvinceName, Address::getCityName, Address::getAreaName)
               .leftJoin(Address.class, Address::getAid, Order::getAid)
               .selectCollection(OrderItem.class, OrderVO::getOrderItems)
               .leftJoin(OrderItem.class, OrderItem::getOid, Order::getOid);
        List<OrderVO> orderVOS = orderMapper.selectJoinList(OrderVO.class, wrapper);
        if(ObjectUtils.isEmpty(orderVOS)) throw new ServiceException("订单数据不存在");
        return orderVOS;
    }

    @Override
    public List<OrderVO> getOrderVOsByS(Integer uid, String username, List<Integer> status)
    {
        userService.judgeUser(uid, username);
        MPJLambdaWrapper<Order> wrapper = new MPJLambdaWrapper<>();
        wrapper.select(Order::getOid, Order::getAid, Order::getStatus, Order::getRecvName,
                       Order::getTotalPrice, Order::getOrderTime, Order::getPayTime)
               .select(Address::getZip, Address::getAddress, Address::getPhone,
                       Address::getProvinceName, Address::getCityName, Address::getAreaName)
               .leftJoin(Address.class, Address::getAid, Order::getAid)
               .eq(Order::getUid, uid)
               .in(Order::getStatus, status)
               .selectCollection(OrderItem.class, OrderVO::getOrderItems)
               .leftJoin(OrderItem.class, OrderItem::getOid, Order::getOid);
        List<OrderVO> orderVOS = orderMapper.selectJoinList(OrderVO.class, wrapper);
        if(ObjectUtils.isEmpty(orderVOS)) throw new ServiceException("订单数据不存在");
        return orderVOS;
    }

    @Override
    public OrderVO getOrderVO(Integer uid, String username, Integer oid)
    {
        userService.judgeUser(uid, username);
        OrderVO orderVO = orderVOMapper.getOrderVO(uid, oid);
        if(ObjectUtils.isEmpty(orderVO)) throw new ServiceException("订单数据不存在");
        return orderVO;
    }

    @Override
    public void setStatus(Integer uid, String username, Integer oid, Integer status)
    {
        userService.judgeUser(uid, username);
        LambdaUpdateWrapper<Order> wrapper = new LambdaUpdateWrapper<>();
        wrapper.set(Order::getStatus, status)
               .eq(Order::getOid, oid)
               .eq(Order::getUid, uid);
        final int row = orderMapper.update(null, wrapper);
        if(row != 1) throw new UpdateException("更新数据时发生异常");
    }

    @Override
    public OrderVO getOrderInfo(Integer uid, String username, Integer oid)
    {
        userService.judgeUser(uid, username);
        Order order = orderMapper.selectOne(new LambdaQueryWrapper<Order>().eq(Order::getUid, uid).eq(Order::getOid, oid));
        if(ObjectUtils.isEmpty(order)) throw new OrderNotFoundException("没有该订单");
        OrderVO orderVO = getOrderVO(uid, username, oid);
        orderVO.setAid(null);
        orderVO.setRecvName(null);
        orderVO.setPhone(null);
        orderVO.setProvinceName(null);
        orderVO.setCityName(null);
        orderVO.setAreaName(null);
        orderVO.setAddress(null);
        orderVO.setStatus(null);
        orderVO.setOrderTime(null);
        orderVO.setPayTime(null);
        orderVO.setZip(null);
        orderVO.setOrderItems(null);
        return orderVO;
    }

    @Override
    public Integer getOCount(Integer uid, String username)
    {
        userService.judgeUser(uid, username);
        return orderMapper.selectCount(null);
    }


    //        @Override
//    public OrderVO getOrderVO(Integer uid, String username, Integer oid)
//    {
//        userService.JudgeUser(uid, username);
//        MPJLambdaWrapper<Order> wrapper = new MPJLambdaWrapper<>();
//        wrapper.select(Order::getOid, Order::getAid, Order::getStatus, Order::getRecvName,
//                       Order::getTotalPrice, Order::getOrderTime, Order::getPayTime)
//               .select(OrderItem::getImage, OrderItem::getTitle, OrderItem::getPrice, OrderItem::getNum)
//               .select(Address::getZip, Address::getAddress, Address::getPhone,
//                       Address::getProvinceName, Address::getCityName, Address::getAreaName)
//               .leftJoin(Address.class, Address::getAid, Order::getAid)
//               .leftJoin(OrderItem.class, OrderItem::getOid, Order::getOid)
//               .eq(Order::getUid, uid)
//               .eq(Order::getOid, oid);
//        OrderVO orderVO = orderVOMapper.selectJoinOne(OrderVO.class, wrapper);
//        if(Objects.isNull(orderVO)) throw new ServiceException("订单数据不存在");
//        return orderVO;
//    }
}
