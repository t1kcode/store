package com.t1k.store.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.t1k.store.entity.District;
import com.t1k.store.mapper.DistrictMapper;
import com.t1k.store.service.IDistrictService;
import com.t1k.store.service.ex.ServiceException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Service
public class DistrictServiceImpl implements IDistrictService
{
    @Resource
    DistrictMapper mapper;

    @Override
    public List<District> getByParent(String parent)
    {
        LambdaQueryWrapper<District> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(District::getParent, parent);
        List<District> list = mapper.selectList(wrapper);
        list.forEach(district -> {
            district.setParent(null);
            district.setId(null);
        });
        return list;
    }

    @Override
    public String getNameByCode(String code)
    {
        final LambdaQueryWrapper<District> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(District::getName)
               .eq(District::getCode, code);
        District district = mapper.selectOne(wrapper);
        if(Objects.isNull(district.getName())) throw new ServiceException("无法获取省市区名称");
        return district.getName();
    }
}
