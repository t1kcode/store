package com.t1k.store.service;

import com.t1k.store.entity.District;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
public class DistrictServiceTests
{
    @Resource
    IDistrictService service;

    @Test
    void getByParent()
    {
        // 86表示中国，所有省的父代号都是86
        final List<District> list = service.getByParent("86");
        list.forEach(System.out::println);
    }
}
