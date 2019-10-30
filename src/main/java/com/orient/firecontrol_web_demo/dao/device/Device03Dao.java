package com.orient.firecontrol_web_demo.dao.device;

import com.orient.firecontrol_web_demo.model.device.Device03;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author bewater
 * @version 1.0
 * @date 2019/10/16 14:37
 * @func 三相子机
 */
@Mapper
@Repository
public interface Device03Dao {
    void insertDevice03Measure(Device03 device03);
}
