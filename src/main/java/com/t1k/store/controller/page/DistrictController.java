package com.t1k.store.controller.page;

import com.t1k.store.controller.BaseController;
import com.t1k.store.entity.District;
import com.t1k.store.entity.JsonResult;
import com.t1k.store.service.IDistrictService;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/user/districts")
@Api(tags = "城市名称相关接口描述")
public class DistrictController extends BaseController
{
    @Resource
    IDistrictService service;

    @ApiOperation(value = "获取城市名称", notes = "<span style='color:red;'>描述:</span>&nbsp;&nbsp;用于获取城市名称，返回List<District>")
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功")
    })
    @ApiImplicitParam(name = "parent", value = "城市父代码", required = true)
    @PostMapping({"/", ""})
    public JsonResult<List<District>> getDistricts(String parent)
    {
        List<District> list = service.getByParent(parent);
        return new JsonResult<>(OK, list);
    }
}
