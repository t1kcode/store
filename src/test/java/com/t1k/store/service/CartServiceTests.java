package com.t1k.store.service;

import com.t1k.store.mapper.CartMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class CartServiceTests
{
    @Resource
    CartMapper mapper;

    @Test
    void test()
    {
        mapper.getCartVOs(1).forEach(System.out::println);
    }

    @Test
    void getCartVOsByCids()
    {
//        Integer[] cids = {2, 3, 4, 7};
//        mapper.getCartVOsByCid(cids).forEach(System.out::println);
//        System.out.println("1");
    }
}
