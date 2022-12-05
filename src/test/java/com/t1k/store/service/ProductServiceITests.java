package com.t1k.store.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class ProductServiceITests
{
    @Resource
    IProductService service;

    @Test
    void searchProducts()
    {
        String keyWord = "戴尔";
//        service.searchProducts(keyWord);

    }
}
