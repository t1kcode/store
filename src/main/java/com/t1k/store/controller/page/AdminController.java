package com.t1k.store.controller.page;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.t1k.store.controller.BaseController;
import com.t1k.store.entity.JsonResult;
import com.t1k.store.entity.Product;
import com.t1k.store.entity.User;
import com.t1k.store.service.IOrderService;
import com.t1k.store.service.IProductService;
import com.t1k.store.service.IUserService;
import com.t1k.store.vo.OrderVO;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/admin")
@Api(tags = "管理员相关接口描述")
public class AdminController extends BaseController
{
    @Resource
    IProductService productService;

    @Resource
    IOrderService orderService;

    @Resource
    IUserService userService;

    // 商品管理
    @ApiOperation(value = "获取商品的数量", notes = "<span style='color:red;'>描述:</span>&nbsp;&nbsp;用于获取商品的数量，返回商品数量")
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 8000, message = "用户未登录")
    })
    @GetMapping("get_pCount")
    public JsonResult<Integer> getPCount(HttpSession session)
    {
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        return new JsonResult<>(OK, productService.getPCount(uid, username));
    }

    @ApiOperation(value = "获取商品信息列表", notes = "<span style='color:red;'>描述:</span>&nbsp;&nbsp;用于用户获取商品信息，返回List<Product>")
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 6004, message = "商品不存在"),
            @ApiResponse(code = 8000, message = "用户未登录")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", required = true),
            @ApiImplicitParam(name = "limit", value = "每页数据条数", required = true),
    })
    @PostMapping("get_products")
    public JsonResult<List<Product>> getProducts(HttpSession session, Long page, Long limit)
    {
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        Page<Product> iPage = new Page<>(page, limit);
        List<Product> products = productService.getProducts(iPage, uid, username);
        return new JsonResult<>(OK, products);
    }

    @ApiOperation(value = "设置商品状态", notes = "<span style='color:red;'>描述:</span>&nbsp;&nbsp;用于用户设置商品状态")
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 4001, message = "数据更新失败"),
            @ApiResponse(code = 8000, message = "用户未登录")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pid", value = "商品ID", required = true),
            @ApiImplicitParam(name = "status", value = "商品状态", required = true)
    })
    @PostMapping("set_s")
    public JsonResult<Void> getS(HttpSession session, Integer pid, Integer status)
    {
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        productService.setStatus(uid, username, pid, status);
        return new JsonResult<>(OK);
    }

    @ApiOperation(value = "获取商品状态", notes = "<span style='color:red;'>描述:</span>&nbsp;&nbsp;用于用户获取商品状态")
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 8000, message = "用户未登录")
    })
    @ApiImplicitParam(name = "pid", value = "商品ID", required = true)
    @PostMapping("get_s")
    public JsonResult<Integer> getS(HttpSession session, Integer pid)
    {
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        return new JsonResult<>(OK, productService.getStatus(uid, username, pid));
    }

    // 订单管理

    @ApiOperation(value = "获取订单列表", notes = "<span style='color:red;'>描述:</span>&nbsp;&nbsp;用于管理员获取订单列表，返回List<OrderVO>")
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
        return new JsonResult<>(OK, orderService.getOrders(uid, username));
    }

    @ApiOperation(value = "获取订单的数量", notes = "<span style='color:red;'>描述:</span>&nbsp;&nbsp;用于获取订单的数量，返回订单数量")
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 8000, message = "用户未登录")
    })
    @GetMapping("get_oCount")
    public JsonResult<Integer> getOCount(HttpSession session)
    {
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        return new JsonResult<>(OK, orderService.getOCount(uid, username));
    }
    // 用户管理
    @ApiOperation(value = "获取所有用户", notes = "<span style='color:red;'>描述:</span>&nbsp;&nbsp;用于获取所有用户，返回用户列表")
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 8000, message = "用户未登录")
    })
    @GetMapping("get_users")
    public JsonResult<List<User>> getUsers(HttpSession session)
    {
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        return new JsonResult<>(OK, userService.getUsers(uid, username));
    }

    @ApiOperation(value = "获取订单的数量", notes = "<span style='color:red;'>描述:</span>&nbsp;&nbsp;用于获取订单的数量，返回订单数量")
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 8000, message = "用户未登录")
    })
    @GetMapping("get_uCount")
    public JsonResult<Integer> getUCount(HttpSession session)
    {
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        return new JsonResult<>(OK, userService.getUCount(uid, username));
    }
}
