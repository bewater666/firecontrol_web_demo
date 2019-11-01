package com.orient.firecontrol_web_demo.controller.alarm;

import com.orient.firecontrol_web_demo.model.common.ResultBean;
import com.orient.firecontrol_web_demo.service.alarm.AlarmService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author bewater
 * @version 1.0
 * @date 2019/10/31 17:31
 * @func
 */
@RestController
@RequestMapping("/alarm")
public class AlarmController {
    @Autowired
    private AlarmService alarmService;

    @ApiOperation(value = "告警信息列表",notes = "告警信息列表,需登录查看")
    @GetMapping("/view")
    @RequiresAuthentication
    public ResultBean list(){
        return alarmService.list();
    }

    /**
     * 查看已处理告警信息列表接口
     * @return
     */
    @ApiOperation(value = "已处理告警列表",notes = "查看已处理告警信息列表,需登录查看")
    @GetMapping("/findHasHandler")
    @RequiresAuthentication
    public ResultBean findHasHandler(){
        return alarmService.findHasHandler();
    }


    /**
     * 查看处理失败的告警信息列表接口
     * @return
     */
    @ApiOperation(value = "处理失败的告警列表",notes = "查看处理失败的告警列表,需登录查看")
    @GetMapping("/findHandlerBad")
    @RequiresAuthentication
    public ResultBean findHandlerBad(){
        return alarmService.findHandlerBad();
    }

    /**
     * 查询未处理的告警信息列表接口
     * @return
     */
    @ApiOperation(value = "未处理的告警列表",notes = "查询未处理的告警信息列表接口 需登录查看")
    @GetMapping("/findUnHandler")
    @RequiresAuthentication
    public ResultBean findUnHandler(){
        return alarmService.findUnHandler();
    }


    /**
     * 根据设备编号查询该设备下的告警信息
     * @param deviceCode
     * @return
     */
    @ApiOperation(value = "查询设备告警",notes = "根据设备编号查询该设备下的告警信息")
    @GetMapping("/findByDeviceCode")
    @RequiresAuthentication
    public ResultBean findByDeviceCode(@RequestParam @ApiParam(name = "deviceCode",value = "设备编号") String deviceCode){
        return alarmService.findByDeviceCode(deviceCode);
    }
}
