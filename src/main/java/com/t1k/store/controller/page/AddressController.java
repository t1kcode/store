package com.t1k.store.controller.page;

import com.t1k.store.controller.BaseController;
import com.t1k.store.entity.Address;
import com.t1k.store.entity.JsonResult;
import com.t1k.store.service.IAddressService;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/address")
@Api(tags = "收货地址接口相关描述")
public class AddressController extends BaseController
{
    @Resource
    IAddressService addressService;

    @ApiOperation(value = "添加收货地址", notes = "<span style='color:red;'>描述:</span>&nbsp;&nbsp;用于用户添加收货地址")
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 4000, message = "数据插入失败"),
            @ApiResponse(code = 5001, message = "用户不存在"),
            @ApiResponse(code = 6001, message = "地址数量已超限制"),
            @ApiResponse(code = 8000, message = "用户未登录")
    })
    @ApiImplicitParam(name = "address", value = "Address对象", required = true)
    @PostMapping("add_address")
    public JsonResult<Void> addAddress(HttpSession session, Address address)
    {
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        addressService.addAddress(uid, username, address);
        return new JsonResult<>(OK);
    }

    @ApiOperation(value = "获取收货地址列表", notes = "<span style='color:red;'>描述:</span>&nbsp;&nbsp;用于用户获取收货地址列表，返回List<Address>")
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 5001, message = "用户不存在"),
            @ApiResponse(code = 8000, message = "用户未登录")
    })
    @GetMapping("get_addressList")
    public JsonResult<List<Address>> getAddressList(HttpSession session)
    {
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        List<Address> list = addressService.getAddressList(uid, username);
        return new JsonResult<>(OK, list);
    }

    @ApiOperation(value = "设置默认收货地址", notes = "<span style='color:red;'>描述:</span>&nbsp;&nbsp;用于用户设置默认收货地址")
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 4001, message = "数据更新失败"),
            @ApiResponse(code = 5001, message = "用户不存在"),
            @ApiResponse(code = 6002, message = "地址不存在"),
            @ApiResponse(code = 6003, message = "非法数据访问"),
            @ApiResponse(code = 8000, message = "用户未登录")
    })
    @ApiImplicitParam(name = "aid", value = "地址ID", required = true)
    @PostMapping("set_default")
    public JsonResult<Void> setDefault(HttpSession session, Integer aid)
    {
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        addressService.setDefault(aid, uid, username);
        return new JsonResult<>(OK);
    }

    @ApiOperation(value = "删除收货地址", notes = "<span style='color:red;'>描述:</span>&nbsp;&nbsp;用于用户删除收货地址")
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 4002, message = "数据删除失败"),
            @ApiResponse(code = 5001, message = "用户不存在"),
            @ApiResponse(code = 6002, message = "地址不存在"),
            @ApiResponse(code = 6003, message = "非法数据访问"),
            @ApiResponse(code = 8000, message = "用户未登录")
    })
    @ApiImplicitParam(name = "aid", value = "地址ID", required = true)
    @PostMapping("del_address")
    public JsonResult<Void> delAddress(HttpSession session, Integer aid)
    {
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        addressService.delAddress(aid, uid, username);
        return new JsonResult<>(OK);
    }

    @ApiOperation(value = "获取收货地址", notes = "<span style='color:red;'>描述:</span>&nbsp;&nbsp;用于用户获取收货地址")
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 5001, message = "用户不存在"),
            @ApiResponse(code = 6002, message = "地址不存在"),
            @ApiResponse(code = 6003, message = "非法数据访问"),
            @ApiResponse(code = 8000, message = "用户未登录")
    })
    @ApiImplicitParam(name = "aid", value = "地址ID", required = true)
    @PostMapping("get_address")
    public JsonResult<Address> getAddress(HttpSession session, Integer aid)
    {
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        Address address = addressService.getAddress(aid, uid, username);
        return new JsonResult<>(OK, address);
    }

    @ApiOperation(value = "修改收货地址", notes = "<span style='color:red;'>描述:</span>&nbsp;&nbsp;用于用户修改收货地址")
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 4001, message = "数据更新失败"),
            @ApiResponse(code = 5001, message = "用户不存在"),
            @ApiResponse(code = 8000, message = "用户未登录")
    })
    @ApiImplicitParam(name = "address", value = "Address对象", required = true)
    @PostMapping("mod_address")
    public JsonResult<Void> modAddress(HttpSession session, Address address)
    {
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        addressService.modAddress(uid, username, address);
        return new JsonResult<>(OK);
    }
}
