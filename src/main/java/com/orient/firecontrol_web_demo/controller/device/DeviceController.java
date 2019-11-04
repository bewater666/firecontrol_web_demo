package com.orient.firecontrol_web_demo.controller.device;

import com.orient.firecontrol_web_demo.config.aop.MyLog;
import com.orient.firecontrol_web_demo.config.page.PageBean;
import com.orient.firecontrol_web_demo.config.page.PageCommons;
import com.orient.firecontrol_web_demo.model.common.ResultBean;
import com.orient.firecontrol_web_demo.model.device.DeviceInfo;
import com.orient.firecontrol_web_demo.service.device.DeviceService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author bewater
 * @version 1.0
 * @date 2019/10/28 14:10
 * @func
 */
@RestController
@RequestMapping("/device")
public class DeviceController {
    @Autowired
    private DeviceService deviceService;


    /**
     * 根据建筑物id  查询该建筑物下的设备列表
     * superadmin admin权限
     * 还是一样  只能查看自己单位下的建筑物下的设备列表
     * 因为前面进来这个接口  已经约束好了 是自己单位下的建筑物了  所以这个建筑物id不会是别的单位的   这里不再再约束了
     * @param buildId
     * @return
     */
    @GetMapping("view/{id}/{currentPage}/{pageSize}")
    @RequiresRoles(value = {"superadmin","admin"},logical = Logical.OR)
    @ApiOperation(value = "查询建筑物下的设备列表",notes = "根据建筑物id 查询该建筑物下的设备列表")
    @ApiImplicitParam(name = "id",value = "建筑物id",required = true,dataType = "int",paramType = "path")
//    @MyLog(description = "查询建筑物下设备")
    public ResultBean findByBuildId(@PathVariable("id") Integer buildId,
                                    @PathVariable("currentPage")@ApiParam(name = "currentPage",value = "当前页码",required = true) Integer currentPage,
                                    @PathVariable("pageSize")@ApiParam(name = "pageSize",value = "每页条数",required = true) Integer pageSize){
        PageBean<DeviceInfo> pageBean = deviceService.findByBuildId(currentPage, pageSize, buildId);
        List<DeviceInfo> items = pageBean.getItems();
        int thisPageNum = PageCommons.getThisPageNum(currentPage, pageSize, pageBean);
        Map map = new HashMap();
        map.put("currentPage",currentPage);
        map.put("thisPageNum",thisPageNum);
        map.put("totalNum", pageBean.getTotalNum());
        map.put("deviceList",items);
        if (items.size()==0){
            return  new ResultBean(200,"该建筑物下无设备信息",null);
        }
        return  new ResultBean(200,"查询成功",map);
    }


    /**
     * 新增设备  并绑定建筑物
     * 这里不需要建筑物id是因为  deviceInfo中有buildCode这个字段 且前端在进行添加时将这个值上下文获取 设置为固定灰白不可改
     * superadmin admin 权限
     * @param deviceInfo
     * @return
     */
    @ApiOperation(value = "新增设备",notes = "这里不需要建筑物id是因为  deviceInfo中有buildCode这个字段 且前端在进行添加时将这个值上下文获取 设置为固定灰白不可改")
    @PostMapping("/add")
    @MyLog(description = "新增设备")
    public ResultBean addDevice(
            @RequestBody @ApiParam(name = "设备bean",value = "传入json格式",required = true) DeviceInfo deviceInfo){
        return deviceService.addDevice(deviceInfo);
    }


}
