package com.t1k.store.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.t1k.store.entity.Address;
import com.t1k.store.entity.Order;
import com.t1k.store.entity.OrderItem;
import com.t1k.store.mapper.OrderVOMapper;
import com.t1k.store.mapper.TestMapper;
import com.t1k.store.vo.OrderVO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class OrderServiceTests
{
    @Resource
    IOrderService service;

    @Resource
    TestMapper mapper;

    @Resource
    OrderVOMapper orderVOMapper;

    @Test
    void test()
    {
//        Integer[] cids = {5};
//        service.createOrder(1, "t1k", 5, cids);
    }

    @Test
    void getOrderVOs()
    {

//        Integer uid = 1;
//        String username = "t1k";
//        MPJLambdaWrapper<Order> wrapper = new MPJLambdaWrapper<>();
//        wrapper.select(Order::getOid, Order::getAid, Order::getStatus, Order::getRecvName,
//                       Order::getTotalPrice, Order::getOrderTime, Order::getPayTime)
//               .select(OrderItem::getImage, OrderItem::getTitle, OrderItem::getPrice, OrderItem::getNum)
//               .select(Address::getZip, Address::getAddress, Address::getPhone,
//                       Address::getProvinceName, Address::getCityName, Address::getAreaName)
//               .leftJoin(Address.class, Address::getAid, Order::getAid)
//               .leftJoin(OrderItem.class, OrderItem::getOid, Order::getOid)
//               .eq(Order::getUid, uid)
//               .orderByAsc(Order::getStatus)
//               .orderByDesc(Order::getCreatedTime);
//        Page<Order> page = new Page<>(1, 2);
//        List<OrderVO> records = mapper.selectJoinPage(page, OrderVO.class, wrapper).getRecords();
//        records.forEach(System.out::println);
    }

    @Test
    void test1()
    {
//        final List<OrderVO> orderVOs = orderVOMapper.getOrderVOs(1);
//        orderVOs.forEach(System.out::println);
    }


    @Test
    void test2()
    {
        Integer uid = 1;
        String username = "t1k";
        ArrayList<Integer> objects = new ArrayList<Integer>(){
            {
                add(4);
                add(3);
            }
        };
        MPJLambdaWrapper<Order> wrapper = new MPJLambdaWrapper<>();
        wrapper.select(Order::getOid, Order::getAid, Order::getStatus, Order::getRecvName,
                       Order::getTotalPrice, Order::getOrderTime, Order::getPayTime)
               .select(Address::getZip, Address::getAddress, Address::getPhone,
                       Address::getProvinceName, Address::getCityName, Address::getAreaName)
               .leftJoin(Address.class, Address::getAid, Order::getAid)
               .eq(Order::getUid, uid)
               .in(Order::getStatus, objects)
               .selectCollection(OrderItem.class, OrderVO::getOrderItems)
               .leftJoin(OrderItem.class, OrderItem::getOid, Order::getOid);

        List<OrderVO> orderVO = mapper.selectJoinList(OrderVO.class, wrapper);
        orderVO.forEach(System.out::println);
//        orderVO.forEach(System.out::println);
//        IPage<OrderVO> orderVOIPage = mapper.selectJoinPage(new Page<>(1, 2), OrderVO.class, wrapper);
//        List<OrderVO> listPage = orderVOIPage.getRecords();
//        listPage.forEach(System.out::println);
    }

    @Test
    void getOrderVO1s()
    {

//        Integer uid = 1;
//        String username = "t1k";
//
//        MPJLambdaWrapper<Order> wrapper = new MPJLambdaWrapper<>();
//        wrapper.select(Order::getOid, Order::getAid, Order::getStatus, Order::getRecvName,
//                       Order::getTotalPrice, Order::getOrderTime, Order::getPayTime)
//               .select(OrderItem::getImage, OrderItem::getTitle, OrderItem::getPrice, OrderItem::getNum)
//               .select(Address::getZip, Address::getAddress, Address::getPhone,
//                       Address::getProvinceName, Address::getCityName, Address::getAreaName)
//               .leftJoin(Address.class, Address::getAid, Order::getAid)
//               .leftJoin(OrderItem.class, OrderItem::getOid, Order::getOid)
//               .eq(Order::getUid, uid)
//               .orderByAsc(Order::getStatus)
//               .orderByDesc(Order::getCreatedTime);
////        Page<Order> page = new Page<>(1, 2);
//        List<OrderVO> records = mapper.selectJoinList(OrderVO.class, wrapper);
//        records.forEach(System.out::println);
    }

    @Test
    void getOrderVO()
    {
        final OrderVO orderVO = orderVOMapper.getOrderVO(1, 42);
        System.out.println(orderVO);
    }
}
