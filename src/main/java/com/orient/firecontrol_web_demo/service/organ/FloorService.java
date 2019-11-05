package com.orient.firecontrol_web_demo.service.organ;

import com.orient.firecontrol_web_demo.config.exception.CustomException;
import com.orient.firecontrol_web_demo.dao.organization.BuildingDao;
import com.orient.firecontrol_web_demo.dao.organization.FloorDao;
import com.orient.firecontrol_web_demo.model.common.ResultBean;
import com.orient.firecontrol_web_demo.model.organization.BuildingInfo;
import com.orient.firecontrol_web_demo.model.organization.FloorInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author bewater
 * @version 1.0
 * @date 2019/11/5 16:53
 * @func
 */
@Service
public class FloorService {
    @Autowired
    private FloorDao floorDao;
    @Autowired
    private BuildingDao buildingDao;


    /**
     * 查询某建筑物下的楼层列表
     * @param buildCode
     * @return
     */
    public ResultBean listByBuildCode(String buildCode){
        BuildingInfo byBuildCode = buildingDao.findByBuildCode(buildCode);
        if (byBuildCode==null){
            throw new CustomException("建筑物编号不存在");
        }
        List<FloorInfo> floorInfos = floorDao.listByBuildCode(buildCode);
        if (floorInfos.size()==0){
            return new ResultBean(200, "当前建筑下无楼层信息", null);
        }
        return new ResultBean(200, "查询楼层列表成功", floorInfos);
    }
}
