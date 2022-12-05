package com.t1k.store.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.t1k.store.entity.Collect;
import com.t1k.store.entity.Product;
import com.t1k.store.mapper.CollectMapper;
import com.t1k.store.mapper.ProductMapper;
import com.t1k.store.service.ICollectService;
import com.t1k.store.service.IProductService;
import com.t1k.store.service.ex.ProductNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

@Service
public class ProductServiceImpl implements IProductService
{
    @Resource
    ProductMapper mapper;

    @Resource
    ICollectService collectService;

    @Override
    public List<Product> getHotProducts(IPage<Product> page)
    {
        LambdaQueryWrapper<Product> wrapper = new LambdaQueryWrapper<>();
        // 必须是上架的商品
        //noinspection unchecked
        wrapper.select(Product::getId, Product::getPrice, Product::getImage, Product::getTitle)
               .eq(Product::getStatus, 1)
               .orderByDesc(Product::getPriority); // 根据优先级进行降序排序
        List<Product> list = mapper.selectPage(page, wrapper).getRecords();
        if(ObjectUtils.isEmpty(list)) throw new ProductNotFoundException("没有商品列表");
        return list;
    }

    @Override
    public Product getProductById(Integer id)
    {
        System.err.println("id = " + id);
        LambdaQueryWrapper<Product> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(Product::getTitle, Product::getSellPoint, Product::getPrice, Product::getImage)
               .eq(Product::getId, id);
        Product product = mapper.selectOne(wrapper);
        if(ObjectUtils.isEmpty(product)) throw new ProductNotFoundException("没有该商品");
        return product;
    }

    @Override
    public List<Product> getNewProducts(IPage<Product> page)
    {
        LambdaQueryWrapper<Product> wrapper = new LambdaQueryWrapper<>();
        //noinspection unchecked
        wrapper.select(Product::getId, Product::getPrice, Product::getImage, Product::getTitle)
               .eq(Product::getStatus, 1)
               .orderByDesc(Product::getCreatedTime); // 根据优先级和上架时间进行降序排序
        List<Product> list = mapper.selectPage(page, wrapper).getRecords();
        if(ObjectUtils.isEmpty(list)) throw new ProductNotFoundException("没有商品列表");
        return list;
    }

    @Override
    public List<Collect> searchProducts(Integer uid, String username, String keyWord)
    {
        List<Collect> collects = new ArrayList<>();
        LambdaQueryWrapper<Product> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(Product::getTitle, keyWord);
        List<Product> products = mapper.selectList(wrapper);
        if(uid == -1 && username.equals("-1"))
        {
            products.forEach(product -> {
                Collect collect = new Collect(){{
                    setPid(product.getId());
                    setPrice(product.getPrice());
                    setImage(product.getImage());
                    setTitle(product.getTitle());
                }};
                collects.add(collect);
            });
        }
        else
        {
            products.forEach(product -> {
                Integer status = collectService.getStatus(uid, username, product.getId());
                Collect collect = new Collect(){{
                    setPid(product.getId());
                    setPrice(product.getPrice());
                    setImage(product.getImage());
                    setTitle(product.getTitle());
                    setStatus(status);
                }};
                collects.add(collect);
            });
        }
        return collects;
    }
}
