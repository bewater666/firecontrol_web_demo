package com.orient.firecontrol_web_demo.service.alarm;

import com.orient.firecontrol_web_demo.config.exception.CustomException;
import com.orient.firecontrol_web_demo.dao.alarm.AlarmDao;
import com.orient.firecontrol_web_demo.dao.device.DeviceInfoDao;
import com.orient.firecontrol_web_demo.model.alarm.AlarmInfo;
import com.orient.firecontrol_web_demo.model.common.ResultBean;
import com.orient.firecontrol_web_demo.model.device.DeviceInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author bewater
 * @version 1.0
 * @date 2019/10/31 17:27
 * @func
 */
@Service
public class AlarmService {
    @Autowired
    private AlarmDao alarmDao;
    @Autowired
    private DeviceInfoDao deviceInfoDao;

    /**
     * 查看告警信息列表
     * @return
     */
    public ResultBean list(){
        List<AlarmInfo> all = alarmDao.findAll();
        if (all.size()==0){
            return new ResultBean(200, "查询成功,当前无告警信息", null);
        }
        return new ResultBean(200, "查询告警信息成功", all);
    }

    /**
     * 查看已处理的告警列表
     * @return
     */
    public ResultBean findHasHandler(){
        List<AlarmInfo> hasHandler = alarmDao.findHasHandler();
        if (hasHandler.size()==0){
            return new ResultBean(200, "已处理告警列表为空", null);
        }
        return new ResultBean(200, "查询已处理告警列表成功", hasHandler);
    }

    /**
     * 查询 处理失败的告警列表
     * @return
     */
    public ResultBean findHandlerBad(){
        List<AlarmInfo> handlerBad = alarmDao.findHandlerBad();
        if (handlerBad.size()==00){
            return new ResultBean(200, "处理失败的告警列表为空", null);
        }
        return new ResultBean(200, "查询处理失败的告警列表成功", handlerBad);
    }

    /**
     * 查询未处理的告警信息列表
     * @return
     */
    public ResultBean findUnHandler(){
        List<AlarmInfo> unHandler = alarmDao.findUnHandler();
        if (unHandler.size()==0){
            return new ResultBean(200, "未处理的告警列表为空", null);
        }
        return new ResultBean(200, "查询未处理的告警列表成功", unHandler);
    }

    /**
     * 根据设备编号 查询该设备下的告警信息
     * @param deviceCode
     * @return
     */
    public ResultBean findByDeviceCode(String deviceCode){
        DeviceInfo one = deviceInfoDao.findOne(deviceCode);
        if (one==null){
            throw new CustomException("该设备编号不存在,请确认后再输入");
        }
        List<AlarmInfo> byDeviceCode = alarmDao.findByDeviceCode(deviceCode);
        if (byDeviceCode.size()==0){
            return new ResultBean(200, "该设备暂无告警信息", null);
        }
        return new ResultBean(200, "查询成功", byDeviceCode);
    }
}
