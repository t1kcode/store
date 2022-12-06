package com.t1k.store.controller.page;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.t1k.store.controller.BaseController;
import com.t1k.store.entity.Collect;
import com.t1k.store.entity.JsonResult;
import com.t1k.store.entity.Product;
import com.t1k.store.service.ICollectService;
import com.t1k.store.service.IProductService;
import io.swagger.annotations.*;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/product")
@Api(tags = "商品相关接口描述")
public class ProductController extends BaseController
{
    @Resource
    IProductService service;

    @Resource
    ICollectService collectService;

    @ApiOperation(value = "获取热销商品", notes = "<span style='color:red;'>描述:</span>&nbsp;&nbsp;用于获取热销商品，返回List<Product>")
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 6004, message = "商品不存在"),
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", required = true),
            @ApiImplicitParam(name = "limit", value = "每页数据条数", required = true),
    })
    @PostMapping("get_hotProducts")
    public JsonResult<List<Product>> getHotProducts(@RequestParam("page") Long page, @RequestParam("limit") Long limit)
    {
        Page<Product> iPage = new Page<>(page, limit);
        List<Product> list = service.getHotProducts(iPage);
        return new JsonResult<>(OK, list);
    }

    @ApiOperation(value = "获取新到商品", notes = "<span style='color:red;'>描述:</span>&nbsp;&nbsp;用于获取新到商品，返回List<Product>")
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 6004, message = "商品不存在"),
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", required = true),
            @ApiImplicitParam(name = "limit", value = "每页数据条数", required = true),
    })
    @PostMapping("get_newProducts")
    public JsonResult<List<Product>> getNewProducts(@RequestParam("page") Long page, @RequestParam("limit") Long limit)
    {
        Page<Product> iPage = new Page<>(page, limit);
        List<Product> list = service.getNewProducts(iPage);
        return new JsonResult<>(OK, list);
    }

    @ApiOperation(value = "获取商品信息", notes = "<span style='color:red;'>描述:</span>&nbsp;&nbsp;用于获取商品信息，返回Product对象")
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 6004, message = "商品不存在"),
    })
    @ApiImplicitParam(name = "id", value = "商品ID", required = true)
    @PostMapping("get_product")
    public JsonResult<Product> getProduct(Integer id)
    {
        return new JsonResult<>(OK, service.getProductById(id));
    }

    @ApiOperation(value = "添加收藏", notes = "<span style='color:red;'>描述:</span>&nbsp;&nbsp;用于用户收藏商品")
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 4000, message = "数据插入失败"),
            @ApiResponse(code = 4001, message = "数据更新失败"),
            @ApiResponse(code = 6004, message = "商品不存在"),
            @ApiResponse(code = 8000, message = "用户未登录")
    })
    @ApiImplicitParam(name = "pid", value = "商品ID", required = true)
    @PostMapping("add_collect")
    public JsonResult<Void> addCollect(HttpSession session, Integer pid)
    {
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        collectService.addCollect(uid, username, pid);
        return new JsonResult<>(OK);
    }

    @ApiOperation(value = "获取收藏商品", notes = "<span style='color:red;'>描述:</span>&nbsp;&nbsp;用于用户获取收藏商品，返回List<Collect>")
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 6004, message = "商品不存在"),
            @ApiResponse(code = 8000, message = "用户未登录")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", required = true),
            @ApiImplicitParam(name = "limit", value = "每页数据条数", required = true),
    })
    @PostMapping("get_collects")
    public JsonResult<List<Collect>> getCollects(HttpSession session, Long page, Long limit)
    {
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        Page<Collect> iPage = new Page<>(page, limit);
        List<Collect> collects = collectService.getCollects(iPage, uid, username);
        return new JsonResult<>(OK, collects);
    }

    @ApiOperation(value = "取消收藏", notes = "<span style='color:red;'>描述:</span>&nbsp;&nbsp;用于用户取消收藏")
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 4001, message = "数据更新失败"),
            @ApiResponse(code = 6004, message = "商品不存在"),
            @ApiResponse(code = 8000, message = "用户未登录")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pid", value = "商品ID", required = true),
            @ApiImplicitParam(name = "status", value = "商品状态", required = true),
    })
    @PostMapping("cancel_collect")
    public JsonResult<Void> cancelCollect(HttpSession session, Integer pid, Integer status)
    {
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        collectService.setStatus(uid, username, pid, status);
        return new JsonResult<>(OK);
    }

    @ApiOperation(value = "获取收藏商品的数量", notes = "<span style='color:red;'>描述:</span>&nbsp;&nbsp;用于获取收藏商品的数量，返回商品数量")
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 8000, message = "用户未登录")
    })
    @GetMapping("get_count")
    public JsonResult<Integer> getCount(HttpSession session)
    {
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        return new JsonResult<>(OK, collectService.getCount(uid, username));
    }

    @ApiOperation(value = "获取收藏商品状态", notes = "<span style='color:red;'>描述:</span>&nbsp;&nbsp;用于用户收藏商品状态")
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 8000, message = "用户未登录")
    })
    @ApiImplicitParam(name = "pid", value = "商品ID", required = true)
    @PostMapping("get_status")
    public JsonResult<Integer> getStatus(HttpSession session, Integer pid)
    {
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        return new JsonResult<>(OK, collectService.getStatus(uid, username, pid));
    }

    @ApiOperation(value = "获取搜索商品", notes = "<span style='color:red;'>描述:</span>&nbsp;&nbsp;用于用户获取搜索商品，返回List<Collect>")
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功"),
    })
    @ApiImplicitParam(name = "keyWord", value = "关键词", required = true)
    @PostMapping("search_product")
    public JsonResult<List<Collect>> searchProduce(HttpSession session, String keyWord)
    {
        Integer uid = -1;
        String username = "-1";
        if(!ObjectUtils.isEmpty(session)){
            uid = getUidFromSession(session);
            username = getUsernameFromSession(session);
        }
        List<Collect> collects = service.searchProducts(uid, username, keyWord);
        return new JsonResult<>(OK, collects);
    }
}
