package com.t1k.store.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.t1k.store.entity.Collect;
import com.t1k.store.entity.Product;

import java.util.List;

/** 处理商品数据的业务层接口 */
public interface IProductService
{
    /**
     * 获取热销商品列表
     * @param page 当前分页的信息，包含页码和每页数据条数
     * @return 热销商品列表
     */
    List<Product> getHotProducts(IPage<Product> page);

    /**
     * 根据商品id获取商品信息
     * @param id 商品id
     * @return 商品信息
     */
    Product getProductById(Integer id);

    /**
     * 获取新到商品列表
     * @param page 当前分页的信息，包含页码和每页数据条数
     * @return 新到商品列表
     */
    List<Product> getNewProducts(IPage<Product> page);

    /**
     * 搜索商品
     * @param uid 当前登录的用户id
     * @param username 当前登录的用户名
     * @param keyWord 关键字
     * @return 符合关键字的商品列表
     */
    List<Collect> searchProducts(Integer uid, String username, String keyWord);
}
