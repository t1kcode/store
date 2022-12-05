package com.t1k.store.controller.page;

import com.alibaba.fastjson.JSON;
import com.t1k.store.controller.BaseController;
import com.t1k.store.entity.JsonResult;
import com.t1k.store.entity.Order;
import com.t1k.store.service.IOrderService;
import com.t1k.store.vo.OrderVO;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@RequestMapping("/order")
@RestController
@Api(tags = "订单相关接口描述")
public class OrderController extends BaseController
{
    @Resource
    IOrderService service;

    @ApiOperation(value = "根据购物车数据创建订单", notes = "<span style='color:red;'>描述:</span>&nbsp;&nbsp;用于用户创建订单，返回Oid")
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
            @ApiImplicitParam(name = "aid", value = "收货地址ID", required = true),
            @ApiImplicitParam(name = "cids", value = "购物车ID", required = true)
    })
    @PostMapping("create_orderByC")
    public JsonResult<Integer> createOrderByC(HttpSession session, Integer aid, String cids)
    {
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        Integer oid = service.createOrderByC(uid, username, aid, JSON.parseArray(cids, Integer.class));
        return new JsonResult<>(OK, oid);
    }

    @ApiOperation(value = "根据商品数据创建订单", notes = "<span style='color:red;'>描述:</span>&nbsp;&nbsp;用于用户创建订单，返回Oid")
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 4000, message = "数据插入失败"),
            @ApiResponse(code = 5001, message = "用户不存在"),
            @ApiResponse(code = 6004, message = "商品不存在"),
            @ApiResponse(code = 8000, message = "用户未登录"),
            @ApiResponse(code = 9000, message = "参数错误")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "aid", value = "收货地址ID", required = true),
            @ApiImplicitParam(name = "pid", value = "商品ID", required = true),
            @ApiImplicitParam(name = "num", value = "商品数量", required = true)
    })
    @PostMapping("create_orderByP")
    public JsonResult<Integer> createOrderByP(HttpSession session, Integer aid, Integer pid, Integer num){
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        Integer oid = service.createOrderByP(uid, username, aid, pid, num);
        return new JsonResult<>(OK, oid);
    }

    @ApiOperation(value = "删除订单", notes = "<span style='color:red;'>描述:</span>&nbsp;&nbsp;用于用户删除订单")
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 5001, message = "用户不存在"),
            @ApiResponse(code = 8000, message = "用户未登录")
    })
    @ApiImplicitParam(name = "oid", value = "订单ID", required = true)
    @PostMapping("del_order")
    public JsonResult<Void> delOrder(HttpSession session, Integer oid)
    {
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        service.deleteOrder(uid, username, oid);
        return new JsonResult<>(OK);
    }

    @ApiOperation(value = "获取订单列表", notes = "<span style='color:red;'>描述:</span>&nbsp;&nbsp;用于用户获取订单列表，返回List<OrderVO>")
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 5001, message = "用户不存在"),
            @ApiResponse(code = 8000, message = "用户未登录"),
            @ApiResponse(code = 9000, message = "订单数据不存在")
    })
    @RequestMapping(value = "get_orderVOs", method = {RequestMethod.GET, RequestMethod.POST})
    public JsonResult<List<OrderVO>> getOrderVOs(HttpSession session)
    {
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        return new JsonResult<>(OK, service.getOrderVOs(uid, username));
    }

    @ApiOperation(value = "获取订单", notes = "<span style='color:red;'>描述:</span>&nbsp;&nbsp;用于用户获取订单，返回OrderVO对象")
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 5001, message = "用户不存在"),
            @ApiResponse(code = 8000, message = "用户未登录"),
            @ApiResponse(code = 9000, message = "参数错误")
    })
    @ApiImplicitParam(name = "oid", value = "订单ID", required = true)
    @PostMapping("get_orderVO")
    public JsonResult<OrderVO> getOrderVO(HttpSession session, Integer oid)
    {
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        return new JsonResult<>(OK, service.getOrderVO(uid, username, oid));
    }

    @ApiOperation(value = "通过订单状态获取订单列表", notes = "<span style='color:red;'>描述:</span>&nbsp;&nbsp;用于用户通过订单状态获取订单列表，返回List<OrderVO>")
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 5001, message = "用户不存在"),
            @ApiResponse(code = 8000, message = "用户未登录"),
            @ApiResponse(code = 9000, message = "订单数据不存在")
    })
    @ApiImplicitParam(name = "oid", value = "订单ID", required = true)
    @PostMapping("get_orderVOsByS")
    public JsonResult<List<OrderVO>> geOrderVOsByS(HttpSession session, String status)
    {
        List<Integer> params = JSON.parseArray(status, Integer.class);
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        return new JsonResult<>(OK, service.getOrderVOsByS(uid, username, params));
    }

    @ApiOperation(value = "确认收货", notes = "<span style='color:red;'>描述:</span>&nbsp;&nbsp;用于用户确认收货")
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 4001, message = "数据更新失败"),
            @ApiResponse(code = 5001, message = "用户不存在"),
            @ApiResponse(code = 8000, message = "用户未登录"),
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "oid", value = "订单ID", required = true),
            @ApiImplicitParam(name = "status", value = "订单状态", required = true)
    })
    @PostMapping("receipt")
    public JsonResult<Void> setStatus(HttpSession session, Integer oid, Integer status)
    {
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        service.setStatus(uid, username, oid, status);
        return new JsonResult<>(OK);
    }

    @ApiOperation(value = "获得订单信息", notes = "<span style='color:red;'>描述:</span>&nbsp;&nbsp;用于获得订单信息，返回OrderVO对象")
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 5001, message = "用户不存在"),
            @ApiResponse(code = 6006, message = "订单数据不存在"),
            @ApiResponse(code = 8000, message = "用户未登录"),
    })
    @ApiImplicitParam(name = "oid", value = "订单ID", required = true)
    @PostMapping(value = "get_orderInfo")
    public JsonResult<OrderVO> getOrderInfo(HttpSession session, Integer oid)
    {
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        return new JsonResult<>(OK, service.getOrderInfo(uid, username, oid));
    }
}
