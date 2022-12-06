package com.t1k.store.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.t1k.store.entity.Collect;
import com.t1k.store.entity.Product;
import com.t1k.store.mapper.CollectMapper;
import com.t1k.store.service.ICollectService;
import com.t1k.store.service.IProductService;
import com.t1k.store.service.IUserService;
import com.t1k.store.service.ex.InsertException;
import com.t1k.store.service.ex.ProductNotFoundException;
import com.t1k.store.service.ex.ServiceException;
import com.t1k.store.service.ex.UpdateException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class CollectServiceImpl implements ICollectService
{
    @Resource
    IUserService userService;

    @Resource
    CollectMapper mapper;

    @Resource
    IProductService productService;

    @Override
    public void addCollect(Integer uid, String username, Integer pid)
    {
        LambdaQueryWrapper<Collect> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(Collect::getId)
               .eq(Collect::getPid, pid);
        Collect data = mapper.selectOne(wrapper);
        if(!Objects.isNull(data)) {
            data.setStatus(1);
            int row = mapper.updateById(data);
            if(row != 1) throw new UpdateException("更新数据时发生异常");
        }
        else
        {
            Product produce = productService.getProductById(pid);
            Collect collect = new Collect();
            collect.setUid(uid);
            collect.setPid(pid);
            collect.setImage(produce.getImage());
            collect.setTitle(produce.getTitle());
            collect.setPrice(produce.getPrice());
            collect.setStatus(1);
            Date date = new Date();
            collect.setCreatedUser(username);
            collect.setModifiedUser(username);
            collect.setModifiedTime(date);
            collect.setCreatedTime(date);
            int row = mapper.insert(collect);
            if(row != 1) throw new InsertException("插入数据时发生异常");
        }
    }

    @Override
    public List<Collect>getCollects(IPage<Collect> iPage, Integer uid, String username)
    {
        userService.JudgeUser(uid, username);
        LambdaQueryWrapper<Collect> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(Collect::getImage, Collect::getTitle, Collect::getPid, Collect::getPrice)
               .eq(Collect::getUid, uid)
               .ne(Collect::getStatus, 0);
        List<Collect> records = mapper.selectPage(iPage, wrapper).getRecords();
        if(ObjectUtils.isEmpty(records)) throw new ProductNotFoundException("没有商品信息");
        return records;
    }

    @Override
    public void setStatus(Integer uid, String username, Integer pid, Integer status)
    {
        userService.JudgeUser(uid, username);
        Product product = productService.getProductById(pid);
        if(Objects.isNull(product)) throw new ProductNotFoundException("没有商品信息");
        LambdaUpdateWrapper<Collect> wrapper = new LambdaUpdateWrapper<>();
        wrapper.set(Collect::getStatus, status)
               .eq(Collect::getPid, pid)
               .eq(Collect::getUid, uid);
        int row = mapper.update(null, wrapper);
        if(row != 1) throw new UpdateException("更新数据时发生异常");
    }

    @Override
    public Integer getStatus(Integer uid, String username, Integer pid)
    {
        userService.JudgeUser(uid, username);
        LambdaQueryWrapper<Collect> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(Collect::getStatus)
               .eq(Collect::getUid, uid)
               .eq(Collect::getPid, pid);
        Collect collect = mapper.selectOne(wrapper);
        Integer status;
        if(Objects.isNull(collect)) {
            status = 404;
        } else{
            status = collect.getStatus();
        }
        return status;
    }

    @Override
    public Integer getCount(Integer uid, String username)
    {
        userService.JudgeUser(uid, username);
        LambdaQueryWrapper<Collect> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Collect::getUid, uid)
               .eq(Collect::getStatus, 1);
        return mapper.selectCount(wrapper);
    }
}
