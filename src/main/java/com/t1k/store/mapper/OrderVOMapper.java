package com.t1k.store.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.yulichang.base.MPJBaseMapper;
import com.t1k.store.vo.OrderVO;

import java.util.List;

public interface OrderVOMapper extends MPJBaseMapper<OrderVO>
{
    List<OrderVO> getOrderVOs(Integer uid);

//    List<OrderVO> getOrderVOsByS(Integer uid, Integer status);

    OrderVO getOrderVO(Integer uid, Integer oid);
}
