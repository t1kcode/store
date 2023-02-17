package com.t1k.store.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.t1k.store.entity.Address;
import com.t1k.store.mapper.AddressMapper;
import com.t1k.store.service.IAddressService;
import com.t1k.store.service.IDistrictService;
import com.t1k.store.service.IUserService;
import com.t1k.store.service.ex.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class AddressServiceImpl implements IAddressService
{
    @Resource
    IUserService userService;
    @Resource
    AddressMapper mapper;
    @Resource
    IDistrictService districtService;

    @Value("${user.address.max-size}")
    private int ADDRESS_MAX_SIZE; // 最大收货地址条数

    @Override
    public void addAddress(Integer uid, String username, Address address)
    {
        userService.judgeUser(uid, username);
        LambdaQueryWrapper<Address> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Address::getUid, uid)
               .eq(Address::getUid, uid);
        int size = Math.toIntExact(mapper.selectCount(wrapper));
        if(size >= ADDRESS_MAX_SIZE) throw new AddressCountLimitException("达到收货地址最大条数");
        address.setUid(uid);
        address.setIsDefault((size == 0 ? 1 : 0)); // 1表示默认，0表示不默认
        String provinceCode = address.getProvinceCode();
        String areaCode = address.getAreaCode();
        String cityCode = address.getCityCode();
        address.setProvinceName(districtService.getNameByCode(provinceCode));
        address.setAreaName(districtService.getNameByCode(areaCode));
        address.setCityName(districtService.getNameByCode(cityCode));
        address.setCreatedUser(username);
        address.setModifiedUser(username);
        Date date = new Date();
        address.setCreatedTime(date);
        address.setModifiedTime(date);
        int rows = mapper.insert(address);
        if(rows != 1) throw new InsertException("插入时产生未知异常");
    }

    @Override
    public List<Address> getAddressList(Integer uid, String username)
    {
        userService.judgeUser(uid, username);
        LambdaQueryWrapper<Address> wrapper = new LambdaQueryWrapper<>();
        //noinspection unchecked
        wrapper.select(Address::getName, Address::getAddress,
                       Address::getTag, Address::getPhone,
                       Address::getCityName, Address::getAreaName, Address::getAid, Address::getProvinceName)
               .eq(Address::getUid, uid)
               .orderByDesc(Address::getIsDefault, Address::getCreatedTime); // is_default为1表示默认，降序排序，没有默认就按创建时间排序
        return mapper.selectList(wrapper);
    }

    @Transactional // 开启事务
    @Override
    public void setDefault(Integer aid, Integer uid, String username)
    {
        userService.judgeUser(uid, username);
        Address address = mapper.selectById(aid);
        if(ObjectUtils.isEmpty(address)) throw new AddressNotFoundException("收获地址不存在");
        if(!address.getUid().equals(uid)) throw new AccessDeniedException("非法数据访问");

        LambdaUpdateWrapper<Address> wrapper = new LambdaUpdateWrapper<>();
        // 将该用户所有收货地址设为非默认
        wrapper.set(Address::getIsDefault, 0)
               .eq(Address::getUid, uid);
        int rows = mapper.update(null, wrapper);
        if(rows < 1) throw new UpdateException("更新数据时产生未知异常");
        address.setIsDefault(1); // 将选中的收获地址设为默认
        address.setModifiedUser(username);
        address.setModifiedTime(new Date());
        int row = mapper.updateById(address);
        if(row < 1) throw new UpdateException("更新数据时产生未知异常");
    }

    @Transactional
    @Override
    public void delAddress(Integer aid, Integer uid, String username)
    {
        userService.judgeUser(uid, username);
        Address address = mapper.selectById(aid);
        if(ObjectUtils.isEmpty(address)) throw new AddressNotFoundException("收获地址不存在");
        if(!address.getUid().equals(uid)) throw new AccessDeniedException("非法数据访问");
        Integer status = address.getIsDefault();
        int row = mapper.deleteById(aid);
        if(row < 1) throw new DeleteException("删除数据时产生未知异常");
        LambdaQueryWrapper<Address> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Address::getUid, uid);
        Integer count = mapper.selectCount(wrapper);
        if(count == 0) return ; // 如果已经没有收货地址，返回空
        if(status == 1){ // 如果删除的这条收货地址为默认收货地址
            wrapper.clear();
            //noinspection unchecked
            wrapper.eq(Address::getUid, uid)
                   .orderByDesc(Address::getModifiedTime)
                   .last("limit 1"); // 将最新修改的第一条收货地址设为默认地址
            Integer aid1 = mapper.selectOne(wrapper).getAid();
            setDefault(aid1, uid, username);
        }
    }

    @Override
    public Address getAddress(Integer aid, Integer uid, String username)
    {
        userService.judgeUser(uid, username);
        Address address = mapper.selectById(aid);
        if(ObjectUtils.isEmpty(address)) throw new AddressNotFoundException("收获地址不存在");
        if(!address.getUid().equals(uid)) throw new AccessDeniedException("非法数据访问");
        return address;
    }

    @Override
    public void modAddress(Integer uid, String username, Address address)
    {
        userService.judgeUser(uid, username);
        address.setModifiedUser(username);
        address.setModifiedTime(new Date());
        int row = mapper.updateById(address);
        if(row < 1) throw new UpdateException("更新数据时产生未知异常");
    }
}
