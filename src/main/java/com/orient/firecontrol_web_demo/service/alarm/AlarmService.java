package com.orient.firecontrol_web_demo.service.alarm;

import com.orient.firecontrol_web_demo.config.exception.CustomException;
import com.orient.firecontrol_web_demo.config.jwt.JwtUtil;
import com.orient.firecontrol_web_demo.dao.alarm.AlarmDao;
import com.orient.firecontrol_web_demo.dao.device.DeviceInfoDao;
import com.orient.firecontrol_web_demo.dao.organization.OrganDao;
import com.orient.firecontrol_web_demo.dao.user.RoleDao;
import com.orient.firecontrol_web_demo.model.alarm.AlarmInfo;
import com.orient.firecontrol_web_demo.model.common.Constant;
import com.orient.firecontrol_web_demo.model.common.ResultBean;
import com.orient.firecontrol_web_demo.model.device.DeviceInfo;
import com.orient.firecontrol_web_demo.model.user.Role;
import org.apache.shiro.SecurityUtils;
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
    @Autowired
    private OrganDao organDao;
    @Autowired
    private RoleDao roleDao;

    /**
     * 查看告警信息列表
     * 各单位只能看各单位的下的告警信息
     * 超级管理员查看所有
     * @return
     */
    public ResultBean list(){
        //获取当前账户
        String account = JwtUtil.getClaim(SecurityUtils.getSubject().getPrincipals().toString(), Constant.ACCOUNT);
        List<Role> byUser = roleDao.findByUser(account);
        for (Role role:
        byUser) {
            String roleName = role.getRoleName();
            if (roleName.equals("superadmin")){ //若是超级管理 该接口就是查看所有单位下的告警列表
                List<AlarmInfo> all = alarmDao.findAll();
                if (all.size()==0){
                    return new ResultBean(200, "查询成功,所有单位均无告警信息", null);
                }
                return new ResultBean(200, "查询所有单位告警信息成功", all);
            }
            if (roleName.equals("admin")){ //若是单位领导 则是查看自己单位下的告警列表
                Integer organId = organDao.findByAccount(account).getId();
                List<AlarmInfo> byOrganId = alarmDao.findByOrganId(organId);
                if (byOrganId.size()==0){
                    return new ResultBean(200, "查询成功,该单位下无告警信息", null);
                }
                return new ResultBean(200, "查询单位告警信息成功", byOrganId);
            }
            throw new CustomException("查询失败");
        }
        return null;
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


    /**
     * 统计各级告警数目
     * @param grade
     * @return
     */
    public ResultBean countAlarmGrade(Integer grade){
        String alarmGrade = "";
        if (grade==0){
            alarmGrade = "0级告警";
            int i = alarmDao.countNum(alarmGrade);
            return new ResultBean(200, "0级告警统级成功", i);
        }
        if (grade==1){
            alarmGrade = "1级告警";
            int i = alarmDao.countNum(alarmGrade);
            return new ResultBean(200, "1级告警统级成功", i);
        }
        if (grade==2){
            alarmGrade = "2级告警";
            int i = alarmDao.countNum(alarmGrade);
            return new ResultBean(200, "2级告警统级成功", i);
        }
        if (grade==3){
            alarmGrade = "3级告警";
            int i = alarmDao.countNum(alarmGrade);
            return new ResultBean(200, "3级告警统级成功", i);
        }
        throw new CustomException("输入告警级别有误,请重新输入");
    }
}
