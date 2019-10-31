package com.orient.firecontrol_web_demo.controller.sysLog;

import com.orient.firecontrol_web_demo.model.common.ResultBean;
import com.orient.firecontrol_web_demo.service.log.SysLogService;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author bewater
 * @version 1.0
 * @date 2019/10/31 15:45
 * @func
 */
@RestController
@RequestMapping("/sysLog")
public class SysLogController {

    @Autowired
    private SysLogService sysLogService;


    /**
     * 查看系统操作日志  登录即可访问
     * @return
     */
    @GetMapping("/view")
    @ApiOperation(value = "日志管理",notes = "查看系统操作日志,登录即可访问")
    @RequiresAuthentication
    public ResultBean list(){
        return sysLogService.list();
    }
}
