package com.orient.firecontrol_web_demo.service.device;

import com.github.pagehelper.PageHelper;
import com.orient.firecontrol_web_demo.config.exception.CustomException;
import com.orient.firecontrol_web_demo.config.page.PageBean;
import com.orient.firecontrol_web_demo.dao.device.DeviceInfoDao;
import com.orient.firecontrol_web_demo.dao.organization.BuildingDao;
import com.orient.firecontrol_web_demo.dao.organization.FloorDao;
import com.orient.firecontrol_web_demo.model.common.ResultBean;
import com.orient.firecontrol_web_demo.model.device.DeviceInfo;
import com.orient.firecontrol_web_demo.model.organization.BuildingInfo;
import com.orient.firecontrol_web_demo.model.organization.FloorInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author bewater
 * @version 1.0
 * @date 2019/10/28 11:43
 * @func
 */
@Service
public class DeviceService {
    @Autowired
    private DeviceInfoDao deviceInfoDao;
    @Autowired
    private BuildingDao buildingDao;
    @Autowired
    private FloorDao floorDao;


    /**
     * 根据建筑物id 获得建筑下的设备列表
     * @param buildingId
     * @return
     */
    public PageBean<DeviceInfo> findByBuildId(Integer currentPage,Integer pageSize,Integer buildingId){

        BuildingInfo byId = buildingDao.findById(buildingId);
        if (byId==null){
            throw new CustomException("查询失败(输入的建筑物id不存在)");
        }
        String buildCode = byId.getBuildCode();
        PageHelper.startPage(currentPage, pageSize);
        List<DeviceInfo> byBuildCode = deviceInfoDao.findByBuildCode(buildCode);
        PageBean<DeviceInfo> pageBean = new PageBean<>(currentPage, pageSize, deviceInfoDao.findByBuildCode(buildCode).size());
        pageBean.setItems(byBuildCode);
        return pageBean;
    }


    /**
     * 新增设备 并绑定建筑及楼层
     * @param deviceInfo
     * @return
     */
    public ResultBean addDevice(DeviceInfo deviceInfo){
        String buildCode = deviceInfo.getBuildCode();
        String deviceCode = deviceInfo.getDeviceCode();
        //其中buildCode 是前端可以根据上下文获得的 所以一般不会出错  并且是固定的
        if (!deviceCode.substring(0, 10).equals(buildCode)){
            throw new CustomException("新增失败,设备编号前10位和建筑物编号不一致");
        }
        DeviceInfo one = deviceInfoDao.findOne(deviceCode);
        if (one!=null){
            throw new CustomException( "新增设备失败,该设备已存在(deviceCode)");
        }
        BuildingInfo byBuildCode = buildingDao.findByBuildCode(buildCode);
        if (byBuildCode==null){
            throw new CustomException("新增设备失败,传入建筑物编码有误");
        }
        //设备类型 deviceType也不需要进行判定  用什么存什么就好
        int i = deviceInfoDao.addDevice(deviceInfo);
        if (i<=0){
            throw new CustomException("新增失败");
        }
        return new ResultBean(200, "新增成功", null);
    }


    /**
     * 查询某建筑物下某楼层下的设备信息
     * @param buildCode
     * @param floorCode
     * @return
     */
    public ResultBean listByBuildCodeAndFloorCode(String buildCode,Integer floorCode){
        BuildingInfo byBuildCode = buildingDao.findByBuildCode(buildCode);
        if (byBuildCode==null){
            throw new CustomException("建筑物编号不存在");
        }
        FloorInfo floorInfo = floorDao.floorIsRight(buildCode, floorCode);
        if (floorInfo==null){
            throw new CustomException("该建筑物下暂无"+floorCode+"楼");
        }
        List<DeviceInfo> byBuildCodeAndFloorCode = deviceInfoDao.findByBuildCodeAndFloorCode(buildCode, floorCode);
        return new ResultBean(200, "查询成功", byBuildCodeAndFloorCode);
    }

}
