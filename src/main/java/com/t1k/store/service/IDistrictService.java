package com.t1k.store.service;

import com.t1k.store.entity.District;

import java.util.List;

/** 处理省市区数据的业务层接口 */
public interface IDistrictService
{
    /**
     * 根据父代号获取省市区信息
     * @param parent 父代号
     * @return 省市区信息
     */
    List<District> getByParent(String parent);

    /**
     * 根据自身代号获取省市区名称
     * @param code 自身代号
     * @return 省市区名称
     */
    String getNameByCode(String code);
}
