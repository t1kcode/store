package com.t1k.store.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.t1k.store.entity.Cart;
import com.t1k.store.vo.CartVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

/** 处理购物车数据操作的持久层接口 */
public interface CartMapper extends BaseMapper<Cart>
{
    @Select("select cid, uid, pid, cart.price, cart.num, product.title, product.image, product.price AS realprice " +
            "from cart left join product on cart.pid = product.id " +
            "where uid=#{uid} " +
            "order by cart.created_time desc")
    List<CartVO> getCartVOs(Integer uid);

    // 注解在使用诸如foreach标签之类时，需要使用<script></script>包裹，
    // 在foreach标签中，
    // 如果参数是数组类型那么keyName为array
    // 如果参数是集合类型那么keyName为list

    @Select("<script> " +
            "select cid, uid, pid, cart.price, cart.num, product.title, product.image, product.price AS realprice " +
            "from cart left join product on cart.pid = product.id " +
            "where cid IN " +
            "<foreach collection='list' open='(' item='cid' separator=',' close=')'> #{cid} </foreach> " +
            "order by cart.created_time desc " +
            "</script>")
    List<CartVO> getCartVOsByCid(@Param("list") List<Integer> cids);

    // 这里类型为List，却需要指定
    @Delete("<script>" +
            "delete from cart " +
            "where uid=#{uid} " +
            "and cid in " +
            "<foreach collection='list' open='(' item='cid' separator=',' close=')'> #{cid} </foreach> " +
            "</script>")
    int selDelCart(Integer uid, @Param("list") List<Integer> cids);
}
