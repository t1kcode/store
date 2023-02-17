package com.t1k.store.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.t1k.store.entity.Cart;
import com.t1k.store.entity.Product;
import com.t1k.store.mapper.CartMapper;
import com.t1k.store.mapper.ProductMapper;
import com.t1k.store.service.ICartService;
import com.t1k.store.service.ex.*;
import com.t1k.store.vo.CartVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class CartServiceImpl implements ICartService
{
    @Resource
    CartMapper mapper;

    @Resource
    ProductMapper productMapper;

    @Resource
    UserServiceImpl userService;

    @Override
    public Integer addToCart(Integer uid, String username, Integer pid, Integer amount)
    {
        Integer cid;
        userService.judgeUser(uid, username);
        LambdaQueryWrapper<Cart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Cart::getUid, uid)
               .eq(Cart::getPid, pid);
        Cart cart = mapper.selectOne(wrapper);
        Date date = new Date();
        if(ObjectUtils.isEmpty(cart)) // 购物车中没有该商品，将该商品添加到购物车
        {
            cart = new Cart();
            cart.setUid(uid);
            cart.setPid(pid);
            cart.setNum(amount);
            LambdaQueryWrapper<Product> wrapper1 = new LambdaQueryWrapper<>();
            wrapper1.eq(Product::getId, pid)
                    .select(Product::getPrice);
            cart.setPrice(productMapper.selectOne(wrapper1).getPrice());
            cart.setCreatedTime(date);
            cart.setModifiedTime(date);
            cart.setCreatedUser(username);
            cart.setModifiedUser(username);
            int row = mapper.insert(cart);
            cid = cart.getCid();
            if(row != 1) throw new InsertException("插入数据时发生未知异常");
        }
        else // 购物车中有该商品，更新该商品的数量
        {
            LambdaUpdateWrapper<Cart> wrapper1 = new LambdaUpdateWrapper<>();
            wrapper1.set(Cart::getNum, amount + cart.getNum())
                    .set(Cart::getModifiedTime, date)
                    .set(Cart::getModifiedUser, username)
                    .eq(Cart::getUid, uid) // 忘记加限定条件，后端数据库cart被污染
                    .eq(Cart::getPid, pid);
            int row = mapper.update(cart, wrapper1);
            cid = cart.getCid();
            if(row != 1) throw new UpdateException("更新数据时发生未知异常");
        }
        return cid;
    }

    @Override
    public List<CartVO> getCartVOs(Integer uid, String username)
    {
        userService.judgeUser(uid, username);
        List<CartVO> cartVOs = mapper.getCartVOs(uid);
        if(ObjectUtils.isEmpty(cartVOs)) throw new CartNotFoundException("没有购物车数据");
        return cartVOs;
    }

    @Override
    public void delCart(Integer uid, String username, Integer cid)
    {
        userService.judgeUser(uid, username);
        LambdaQueryWrapper<Cart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Cart::getCid, cid);
        int row = mapper.delete(wrapper);
        if(row != 1) throw new DeleteException("删除数据时发生未知异常");
    }

    @Override
    public void selDelCart(Integer uid, String username, List<Integer> cids)
    {
        if(ObjectUtils.isEmpty(cids)) throw new ServiceException("参数错误");
        userService.judgeUser(uid, username);
        final int row = mapper.selDelCart(uid, cids);
        if(row < 1) throw new CartNotFoundException("没有购物车数据");
    }

    @Override
    public void changeCartNum(Integer uid, String username, Integer cid, Integer num)
    {
        userService.judgeUser(uid, username);
        LambdaUpdateWrapper<Cart> wrapper = new LambdaUpdateWrapper<>();
        wrapper.set(Cart::getNum, num);
        wrapper.eq(Cart::getCid, cid);
        int row = mapper.update(null, wrapper);
        if(row != 1) throw new UpdateException("更新数据时发生未知异常");
    }

    @Override
    public List<CartVO> getCartVOsByCids(Integer uid, String username, List<Integer> cids)
    {
        if(ObjectUtils.isEmpty(cids)) throw new ServiceException("参数错误");
        userService.judgeUser(uid, username);
        List<CartVO> list = mapper.getCartVOsByCid(cids);
        if(ObjectUtils.isEmpty(list)) throw new CartNotFoundException("没有购物车数据");
        list.forEach(cartVO -> {
            if(!cartVO.getUid().equals(uid))
                list.remove(cartVO);
        });
        return list;
    }
}
