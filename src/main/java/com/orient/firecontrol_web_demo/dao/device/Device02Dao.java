package com.orient.firecontrol_web_demo.dao.device;

import com.orient.firecontrol_web_demo.model.device.Device02;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author bewater
 * @version 1.0
 * @date 2019/10/16 14:37
 * @func 单相子机
 */
@Mapper
@Repository
public interface Device02Dao {
    void insertDevice02Measure(Device02 device02);
}
