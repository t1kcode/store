package com.t1k.store.controller.page;

import com.alibaba.fastjson.JSON;
import com.t1k.store.controller.BaseController;
import com.t1k.store.entity.JsonResult;
import com.t1k.store.service.ICartService;
import com.t1k.store.service.ex.ServiceException;
import com.t1k.store.vo.CartVO;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.IntFunction;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cart")
@Api(tags = "购物车接口相关描述")
public class CartController extends BaseController
{
    @Resource
    ICartService service;

    @ApiOperation(value = "加入购物车", notes = "<span style='color:red;'>描述:</span>&nbsp;&nbsp;用于用户将商品加入购物车")
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 4000, message = "数据插入失败"),
            @ApiResponse(code = 5001, message = "用户不存在"),
            @ApiResponse(code = 6004, message = "商品不存在"),
            @ApiResponse(code = 6005, message = "购物车数据不存在"),
            @ApiResponse(code = 8000, message = "用户未登录"),
            @ApiResponse(code = 9000, message = "参数错误")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pid", value = "商品ID", required = true),
            @ApiImplicitParam(name = "amount", value = "商品数量", required = true)
    })
    @PostMapping("add_to_cart")
    public JsonResult<Integer> addToCart(HttpSession session,
                                      @RequestParam("id") Integer pid,
                                      @RequestParam("num") Integer amount)
    {
        String username = getUsernameFromSession(session);
        Integer uid = getUidFromSession(session);
        service.addToCart(uid, username, pid, amount);
        return new JsonResult<>(OK, service.addToCart(uid, username, pid, amount));
    }

    @ApiOperation(value = "加入购物车", notes = "<span style='color:red;'>描述:</span>&nbsp;&nbsp;用于用户将商品加入购物车")
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 5001, message = "用户不存在"),
            @ApiResponse(code = 6005, message = "购物车数据不存在"),
            @ApiResponse(code = 8000, message = "用户未登录")
    })
    @GetMapping("get_cartVOs")
    public JsonResult<List<CartVO>> getCartVOs(HttpSession session)
    {
        String username = getUsernameFromSession(session);
        Integer uid = getUidFromSession(session);
        return new JsonResult<>(OK, service.getCartVOs(uid, username));
    }

    @ApiOperation(value = "删除购物车商品", notes = "<span style='color:red;'>描述:</span>&nbsp;&nbsp;用于用户删除购物车商品")
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 4002, message = "数据删除失败"),
            @ApiResponse(code = 5001, message = "用户不存在"),
            @ApiResponse(code = 8000, message = "用户未登录")
    })
    @ApiImplicitParam(name = "cid", value = "购物车ID", required = true)
    @PostMapping("del_cart")
    public JsonResult<Void> delCart(HttpSession session, Integer cid)
    {
        String username = getUsernameFromSession(session);
        Integer uid = getUidFromSession(session);
        service.delCart(uid, username, cid);
        return new JsonResult<>(OK);
    }

    @ApiOperation(value = "选择购物车商品删除", notes = "<span style='color:red;'>描述:</span>&nbsp;&nbsp;用于用户选择购物车商品删除")
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 5001, message = "用户不存在"),
            @ApiResponse(code = 6005, message = "购物车数据不存在"),
            @ApiResponse(code = 8000, message = "用户未登录"),
            @ApiResponse(code = 9000, message = "参数错误")
    })
    @ApiImplicitParam(name = "cids", value = "购物车ID", required = true)
    @PostMapping("sel_del_cart")
    public JsonResult<Void> selDelCart(HttpSession session, String cids)
    {
        // 前端用json传送数据，后端需要加上@RequestBody
        // 使用fastjson将前端传回的JsonString转换为List
        List<Integer> params = JSON.parseArray(cids, Integer.class);
        String username = getUsernameFromSession(session);
        Integer uid = getUidFromSession(session);
        service.selDelCart(uid, username, params);
        return new JsonResult<>(OK);
    }

    @ApiOperation(value = "改变购物车商品数量", notes = "<span style='color:red;'>描述:</span>&nbsp;&nbsp;用于用户改变购物车商品数量")
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 4001, message = "数据更新失败"),
            @ApiResponse(code = 5001, message = "用户不存在"),
            @ApiResponse(code = 8000, message = "用户未登录")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cid", value = "购物车ID", required = true),
            @ApiImplicitParam(name = "num", value = "商品数量", required = true)
    })
    @PostMapping("change_cartNum")
    public JsonResult<Void> changeCartNum(HttpSession session, Integer cid, Integer num)
    {
        String username = getUsernameFromSession(session);
        Integer uid = getUidFromSession(session);
        service.changeCartNum(uid, username, cid, num);
        return new JsonResult<>(OK);
    }

    @ApiOperation(value = "改变购物车商品数量", notes = "<span style='color:red;'>描述:</span>&nbsp;&nbsp;用于用户改变购物车商品数量")
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 5001, message = "用户不存在"),
            @ApiResponse(code = 6005, message = "购物车数据不存在"),
            @ApiResponse(code = 8000, message = "用户未登录"),
            @ApiResponse(code = 9000, message = "参数错误")
    })
    @ApiImplicitParam(name = "cids", value = "购物车ID", required = true)
    @PostMapping("get_cartVOsByCids")
    public JsonResult<List<CartVO>> getCartVOsByCids(HttpSession session, String cids)
    {
        String username = getUsernameFromSession(session);
        Integer uid = getUidFromSession(session);
        List<CartVO> cartVOs = service.getCartVOsByCids(uid, username, JSON.parseArray(cids, Integer.class));
        return new JsonResult<>(OK, cartVOs);
    }
}
