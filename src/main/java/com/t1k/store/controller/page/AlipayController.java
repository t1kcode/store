package com.t1k.store.controller.page;

import com.t1k.store.controller.BaseController;
import com.t1k.store.entity.JsonResult;
import com.t1k.store.service.impl.AlipayService;
import com.t1k.store.vo.OrderVO;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/alipay")
@Api(tags = "支付宝沙盒相关接口描述")
public class AlipayController extends BaseController
{
    @Resource
    AlipayService alipayService;

    @ApiOperation(value = "创建支付宝付款页面", notes = "<span style='color:red;'>描述:</span>&nbsp;&nbsp;用于创建支付宝付款页面，返回form表单")
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功"),
    })
    @ApiImplicitParam(name = "order", value = "OrderVO对象", required = true)
    @PostMapping(value = "create", produces = "text/html; charset=utf-8")
    public String create(Integer oid, String totalPrice)
    {
        return alipayService.pay(oid, totalPrice);
    }
}
