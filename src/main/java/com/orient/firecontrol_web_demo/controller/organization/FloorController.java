package com.orient.firecontrol_web_demo.controller.organization;

import com.orient.firecontrol_web_demo.model.common.ResultBean;
import com.orient.firecontrol_web_demo.service.organ.FloorService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author bewater
 * @version 1.0
 * @date 2019/11/5 16:58
 * @func
 */
@RestController
@RequestMapping("/floor")
public class FloorController {
    @Autowired
    private FloorService floorService;

    @GetMapping("/view")
    @ApiOperation(value = "楼层列表",notes = "楼层列表,并判断楼层状态,超级管理员,单位领导可访问")
    @RequiresRoles(value = {"superadmin","admin"},logical = Logical.OR)
    public ResultBean list(@RequestParam("buildCode") @ApiParam(name = "buildCode",value = "建筑物编号",required = true) String buildCode){
        return floorService.listByBuildCode(buildCode);
    }
}
